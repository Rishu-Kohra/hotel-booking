package com.hotelbooking.controllers;

import com.hotelbooking.dto.BookingRequest;
import com.hotelbooking.models.Booking;
import com.hotelbooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> createBooking(@PathVariable String customerId, 
                                               @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(customerId, request));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Booking>> getCustomerBookings(@PathVariable String customerId) {
        return ResponseEntity.ok(bookingService.getCustomerBookings(customerId));
    }

    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<Booking>> getHotelBookings(@PathVariable String hotelId) {
        return ResponseEntity.ok(bookingService.getHotelBookings(hotelId));
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok().build();
    }
} 