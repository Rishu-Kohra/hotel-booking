package com.hotelbooking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.RoomType;
import com.hotelbooking.repositories.BookingRepository;
import com.hotelbooking.services.BookingService;

public class BookingCancelTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = spy(bookingService); // Create a partial mock
    }

    @Test
    public void testCancelBooking() {
        String bookingId = "booking123";

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setCheckInDate(LocalDate.now().plusDays(1));
        booking.setCheckoutDate(LocalDate.now().plusDays(5));
        booking.setNumberOfRooms(2);

        Hotel hotel = new Hotel();
        hotel.setHotelId("hotel123");
        booking.setHotel(hotel);

        RoomType roomType = new RoomType();
        roomType.setRoomTypeId("roomType123");
        booking.setRoomType(roomType);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        doNothing().when(bookingService).updateInventory(hotel, roomType, booking.getCheckInDate(), booking.getCheckoutDate(), booking.getNumberOfRooms(), true);

        bookingService.cancelBooking(bookingId);

        verify(bookingRepository).delete(booking);
    }

    @Test
    public void testCancelBookingNotFound() {
        String bookingId = "booking123";

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking(bookingId);
        });

        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    public void testCancelPastBooking() {
        String bookingId = "booking123";

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setCheckInDate(LocalDate.now().minusDays(1));
        booking.setCheckoutDate(LocalDate.now().plusDays(5));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking(bookingId);
        });

        assertEquals("Cannot cancel past bookings", exception.getMessage());
    }
}
