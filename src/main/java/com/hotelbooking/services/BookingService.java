package com.hotelbooking.services;

import com.hotelbooking.dto.BookingRequest;
import com.hotelbooking.models.*;
import com.hotelbooking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Booking createBooking(String customerId, BookingRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        validateBookingDates(request.getCheckInDate(), request.getCheckoutDate());
        validateRoomAvailability(hotel, roomType, request.getCheckInDate(), 
                               request.getCheckoutDate(), request.getNumberOfRooms());

        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setCustomer(customer);
        booking.setHotel(hotel);
        booking.setRoomType(roomType);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckoutDate(request.getCheckoutDate());
        booking.setNumberOfRooms(request.getNumberOfRooms());
        booking.setTotalPrice(calculateTotalPrice(roomType.getPrice(), request.getCheckInDate(), 
                                                request.getCheckoutDate(), request.getNumberOfRooms()));

        updateInventory(hotel, roomType, request.getCheckInDate(), request.getCheckoutDate(), 
                       request.getNumberOfRooms(), false);

        return bookingRepository.save(booking);
    }

    private void validateBookingDates(LocalDate checkIn, LocalDate checkout) {
        if (checkIn.isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        if (checkout.isBefore(checkIn)) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }
    }

    private void validateRoomAvailability(Hotel hotel, RoomType roomType, LocalDate checkIn, 
                                        LocalDate checkout, Integer numberOfRooms) {
        List<Inventory> availableRooms = inventoryRepository.findAvailableRooms(
                hotel, roomType, checkIn, checkout, numberOfRooms);

        //System.out.println(availableRooms.size());
        
        if (availableRooms.size() != ChronoUnit.DAYS.between(checkIn, checkout)) {
            throw new RuntimeException("Requested rooms are not available for the selected dates");
        }
    }

    private Double calculateTotalPrice(Integer pricePerNight, LocalDate checkIn, 
                                     LocalDate checkout, Integer numberOfRooms) {
        long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkout);
        return pricePerNight * numberOfNights * numberOfRooms * 1.0;
    }

    @Transactional
	public void updateInventory(Hotel hotel, RoomType roomType, LocalDate checkIn, 
                               LocalDate checkout, Integer numberOfRooms, boolean isCancel) {
        long days = ChronoUnit.DAYS.between(checkIn, checkout); //days =2
        for (int i = 0; i < days; i++) {
            final LocalDate currentDate = checkIn.plusDays(i);
            Inventory inventory = inventoryRepository
                    .findByHotelAndRoomTypeAndDate(hotel, roomType, currentDate)
                    .orElseGet(() -> {
                        Inventory newInventory = new Inventory();
                        newInventory.setInventoryId(UUID.randomUUID().toString());
                        newInventory.setHotel(hotel);
                        newInventory.setRoomType(roomType);
                        newInventory.setDate(currentDate);
                        newInventory.setAvailableRooms(roomType.getTotalRooms());
                        return newInventory;
                    });

            if (isCancel) {
                inventory.setAvailableRooms(inventory.getAvailableRooms() + numberOfRooms);
            } else {
                if (inventory.getAvailableRooms() < numberOfRooms) {
                    throw new RuntimeException("Not enough rooms available for " + currentDate);
                }
                inventory.setAvailableRooms(inventory.getAvailableRooms() - numberOfRooms);
            }

            inventoryRepository.save(inventory);
        }
    }

    public List<Booking> getCustomerBookings(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return bookingRepository.findByCustomer(customer);
    }

    public List<Booking> getHotelBookings(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return bookingRepository.findByHotel(hotel);
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot cancel past bookings");
        }

        updateInventory(booking.getHotel(), booking.getRoomType(), booking.getCheckInDate(),
                       booking.getCheckoutDate(), booking.getNumberOfRooms(), true);

        bookingRepository.delete(booking);
    }
    
    	 
	@Transactional
	public void deleteBookingByCustomer(String customerId) {
		List<Booking> bookings = getCustomerBookings(customerId);
		for (Booking booking : bookings) {
			String bookingId = booking.getBookingId();
			cancelBooking(bookingId);
		}
	}
	
	public List<Booking> getRoomTypeBookings(String roomTypeId) {
    	RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("RoomType not found"));
        return bookingRepository.findByRoomType(roomType);
    }
} 