package com.hotelbooking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.repositories.BookingRepository;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.services.BookingService;

public class GetHotelBookingTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHotelBookings() {
        String hotelId = "hotel123";

        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);

        Booking booking1 = new Booking();
        booking1.setBookingId("booking1");
        booking1.setHotel(hotel);

        Booking booking2 = new Booking();
        booking2.setBookingId("booking2");
        booking2.setHotel(hotel);

        List<Booking> bookings = Arrays.asList(booking1, booking2);

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(bookingRepository.findByHotel(hotel)).thenReturn(bookings);

        List<Booking> result = bookingService.getHotelBookings(hotelId);

        assertEquals(2, result.size());
        assertEquals("booking1", result.get(0).getBookingId());
        assertEquals("booking2", result.get(1).getBookingId());
    }

    @Test
    public void testGetHotelBookingsHotelNotFound() {
        String hotelId = "hotel123";

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.getHotelBookings(hotelId);
        });

        assertEquals("Hotel not found", exception.getMessage());
    }
}
