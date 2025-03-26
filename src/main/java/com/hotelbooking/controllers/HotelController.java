package com.hotelbooking.controllers;

import com.hotelbooking.dto.HotelRequest;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "http://localhost:3000")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Hotel> createHotel(@RequestParam String ownerId, 
                                           @RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.createHotel(ownerId, request));
    }

    @PutMapping("/{hotelId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelId, 
                                           @RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.updateHotel(hotelId, request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String searchTerm) {
        return ResponseEntity.ok(hotelService.searchHotels(searchTerm));
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<Hotel>> getHotelsByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(hotelService.getHotelsByOwner(ownerId));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Hotel>> getHotelsByCity(@PathVariable String city) {
        return ResponseEntity.ok(hotelService.getHotelsByCity(city));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.ok().build();
    }
} 