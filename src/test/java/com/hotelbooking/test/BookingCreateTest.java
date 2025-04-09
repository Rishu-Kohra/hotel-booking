package com.hotelbooking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hotelbooking.dto.BookingRequest;
import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.RoomType;
import com.hotelbooking.repositories.BookingRepository;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.RoomTypeRepository;
import com.hotelbooking.services.BookingService;

public class BookingCreateTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = spy(bookingService); // Create a partial mock
    }

//    @Test
//    public void testCreateBooking() {
//        String customerId = "customer123";
//        BookingRequest request = new BookingRequest();
//        request.setHotelId("hotel123");
//        request.setRoomTypeId("roomType123");
//        request.setCheckInDate(LocalDate.of(2025, 4, 10));
//        request.setCheckoutDate(LocalDate.of(2025, 4, 15));
//        request.setNumberOfRooms(2);
//
//        Customer customer = new Customer();
//        customer.setCustomerId(customerId);
//
//        Hotel hotel = new Hotel();
//        hotel.setHotelId("hotel123");
//
//        RoomType roomType = new RoomType();
//        roomType.setRoomTypeId("roomType123");
//        roomType.setPrice(100);
//
//        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
//        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotel));
//        when(roomTypeRepository.findById(request.getRoomTypeId())).thenReturn(Optional.of(roomType));
//        doNothing().when(bookingService).validateBookingDates(request.getCheckInDate(), request.getCheckoutDate());
//        doNothing().when(bookingService).validateRoomAvailability(hotel, roomType, request.getCheckInDate(), request.getCheckoutDate(), request.getNumberOfRooms());
//
//        Booking booking = new Booking();
//        booking.setBookingId(UUID.randomUUID().toString());
//        booking.setCustomer(customer);
//        booking.setHotel(hotel);
//        booking.setRoomType(roomType);
//        booking.setCheckInDate(request.getCheckInDate());
//        booking.setCheckoutDate(request.getCheckoutDate());
//        booking.setNumberOfRooms(request.getNumberOfRooms());
//        booking.setTotalPrice(100.0); // price * days * rooms
//
//        when(bookingRepository.save(booking)).thenReturn(booking);
//
//        Booking result = bookingService.createBooking(customerId, request);
//
//        //assertEquals(booking.getBookingId(), result.getBookingId());
////        assertEquals(booking.getCustomer(), result.getCustomer());
////        assertEquals(booking.getHotel(), result.getHotel());
////        assertEquals(booking.getRoomType(), result.getRoomType());
////        assertEquals(booking.getCheckInDate(), result.getCheckInDate());
////        assertEquals(booking.getCheckoutDate(), result.getCheckoutDate());
////        assertEquals(booking.getNumberOfRooms(), result.getNumberOfRooms());
////        assertEquals(booking.getTotalPrice(), result.getTotalPrice());
//        assertEquals(booking.getHotel().getHotelId(),result.getHotel().getHotelId());
//    }

    @Test
    public void testCreateBookingCustomerNotFound() {
        String customerId = "customer123";
        BookingRequest request = new BookingRequest();
        request.setHotelId("hotel123");
        request.setRoomTypeId("roomType123");
        request.setCheckInDate(LocalDate.of(2025, 4, 10));
        request.setCheckoutDate(LocalDate.of(2025, 4, 15));

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(customerId, request);
        });

        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    public void testCreateBookingHotelNotFound() {
        String customerId = "customer123";
        BookingRequest request = new BookingRequest();
        request.setHotelId("hotel123");
        request.setRoomTypeId("roomType123");
        request.setCheckInDate(LocalDate.of(2025, 4, 10));
        request.setCheckoutDate(LocalDate.of(2025, 4, 15));

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(customerId, request);
        });

        assertEquals("Hotel not found", exception.getMessage());
    }

    @Test
    public void testCreateBookingRoomTypeNotFound() {
        String customerId = "customer123";
        BookingRequest request = new BookingRequest();
        request.setHotelId("hotel123");
        request.setRoomTypeId("roomType123");
        request.setCheckInDate(LocalDate.of(2025, 4, 10));
        request.setCheckoutDate(LocalDate.of(2025, 4, 15));

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Hotel hotel = new Hotel();
        hotel.setHotelId("hotel123");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotel));
        when(roomTypeRepository.findById(request.getRoomTypeId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(customerId, request);
        });

        assertEquals("Room type not found", exception.getMessage());
    }
}

