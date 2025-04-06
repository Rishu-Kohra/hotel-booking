package com.hotelbooking.controllers;

import com.hotelbooking.dto.RoomTypeRequest;
import com.hotelbooking.models.Booking;
import com.hotelbooking.models.RoomType;
import com.hotelbooking.services.BookingService;
import com.hotelbooking.services.InventoryService;
import com.hotelbooking.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roomTypes")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RoomType> createRoomType(@RequestBody RoomTypeRequest request) {
        return ResponseEntity.ok(roomTypeService.createRoomType(request));
    }

    @PutMapping("/{roomTypeId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable String roomTypeId, 
                                                 @RequestBody RoomTypeRequest request) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(roomTypeId, request));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomType>> getRoomTypesByHotel(@PathVariable String hotelId) {
        return ResponseEntity.ok(roomTypeService.getRoomTypesByHotel(hotelId));
    }

    @GetMapping("/hotel/{hotelId}/maxPrice/{maxPrice}")
    public ResponseEntity<List<RoomType>> getRoomTypesByHotelAndMaxPrice(
            @PathVariable String hotelId, @PathVariable Integer maxPrice) {
        return ResponseEntity.ok(roomTypeService.getRoomTypesByHotelAndMaxPrice(hotelId, maxPrice));
    }

    @GetMapping("/{roomTypeId}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable String roomTypeId) {
        return ResponseEntity.ok(roomTypeService.getRoomTypeById(roomTypeId));
    }

    @DeleteMapping("/{roomTypeId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteRoomType(@PathVariable String roomTypeId) {
    	List<Booking> bookings = bookingService.getRoomTypeBookings(roomTypeId);
		for(Booking booking : bookings) {
			String bookingId = booking.getBookingId();
			bookingService.cancelBooking(bookingId);
		}
		inventoryService.deleteInventoryByRoomType(roomTypeId);
        roomTypeService.deleteRoomType(roomTypeId);
        return ResponseEntity.ok().build();
    }
} 