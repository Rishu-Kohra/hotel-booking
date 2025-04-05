package com.hotelbooking.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Inventory;
import com.hotelbooking.models.Owner;
import com.hotelbooking.models.RoomType;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.InventoryRepository;
import com.hotelbooking.repositories.OwnerRepository;
import com.hotelbooking.repositories.RoomTypeRepository;

@Service
public class InventoryService {
	@Autowired
    private InventoryRepository inventoryRepository;
	
	@Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;
	
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
	
	@Transactional
	public List<Inventory> initializeInventory(String roomTypeId){
		RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));
		LocalDate startDate;
		List<Inventory> existingInventory = inventoryRepository.findByRoomTypeRoomTypeId(roomTypeId);
		if(existingInventory.isEmpty()) {
			startDate = LocalDate.now();
		} else {
			LocalDate lastDate = existingInventory.stream()
					.map(Inventory::getDate)
					.max(LocalDate::compareTo)
					.orElse(LocalDate.now());
			startDate = lastDate.plusDays(1);
		}
		LocalDate endDate = startDate.plusMonths(1);
		List<Inventory> roomTypeInventory = new ArrayList<>();
		for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
			Inventory inventory = new Inventory();
			inventory.setInventoryId(UUID.randomUUID().toString());
			inventory.setAvailableRooms(roomType.getTotalRooms());
			inventory.setRoomType(roomType);
			inventory.setHotel(roomType.getHotel());
			inventory.setDate(date);
			inventoryRepository.save(inventory);
			roomTypeInventory.add(inventory);
		}
		return roomTypeInventory;
	}
}
