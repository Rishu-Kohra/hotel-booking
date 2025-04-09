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
import com.hotelbooking.models.Customer;
import com.hotelbooking.repositories.BookingRepository;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.services.BookingService;

public class GetCustomerBookingTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerBookings() {
        String customerId = "customer123";

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Booking booking1 = new Booking();
        booking1.setBookingId("booking1");
        booking1.setCustomer(customer);

        Booking booking2 = new Booking();
        booking2.setBookingId("booking2");
        booking2.setCustomer(customer);

        List<Booking> bookings = Arrays.asList(booking1, booking2);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(bookingRepository.findByCustomer(customer)).thenReturn(bookings);

        List<Booking> result = bookingService.getCustomerBookings(customerId);

        assertEquals(2, result.size());
        assertEquals("booking1", result.get(0).getBookingId());
        assertEquals("booking2", result.get(1).getBookingId());
    }

    @Test
    public void testGetCustomerBookingsCustomerNotFound() {
        String customerId = "customer124";

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.getCustomerBookings(customerId);
        });

        assertEquals("Customer not found", exception.getMessage());
    }
}
