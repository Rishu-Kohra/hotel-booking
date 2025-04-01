package com.hotelbooking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Inventory;
import com.hotelbooking.models.Owner;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.InventoryRepository;
import com.hotelbooking.repositories.OwnerRepository;

@Service
public class InventoryService {
	@Autowired
    private InventoryRepository inventoryRepository;
	
	@Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OwnerRepository ownerRepository;
	
	public List<List<Inventory>> getInventoryByOwner(String ownerId) {
		Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        List<Hotel> hotelList = hotelRepository.findByOwner(owner);
        List<List<Inventory>> hotelInventory = new ArrayList<>();
        for(Hotel hotel: hotelList) {
        	List<Inventory> inventory = inventoryRepository.findHotelInventory(hotel);
        	hotelInventory.add(inventory);
        }
        return hotelInventory;
    }
}
