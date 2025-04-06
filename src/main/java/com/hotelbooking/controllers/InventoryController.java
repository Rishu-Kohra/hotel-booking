package com.hotelbooking.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Inventory;
import com.hotelbooking.services.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {
	@Autowired
    private InventoryService inventoryService;
	
	@GetMapping("/{ownerId}")
    @PreAuthorize("hasRole('OWNER')")
    public List<List<Inventory>> getHotelInventory(@PathVariable String ownerId) {
        return inventoryService.getInventoryByOwner(ownerId);
    }
	
	@PostMapping("/intialize/{roomTypeId}")
    @PreAuthorize("hasRole('OWNER')")
    public List<Inventory> initializeInventory(@PathVariable String roomTypeId) {
		return inventoryService.initializeInventory(roomTypeId);
    }
	
	@GetMapping("/available-hotels/{checkInDate}/{checkOutDate}/{city}")
    public ResponseEntity<List<Hotel>> getAvailableRoomsByCityAndDate(@PathVariable LocalDate checkInDate, 
    		@PathVariable LocalDate checkOutDate, @PathVariable String city) {
        return ResponseEntity.ok(inventoryService.getAvailableRoomsByCityAndDate(city, checkInDate, checkOutDate));
    }
	
}
