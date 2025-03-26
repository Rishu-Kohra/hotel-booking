package com.hotelbooking.services;

import com.hotelbooking.dto.RoomTypeRequest;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.RoomType;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    public RoomType createRoomType(RoomTypeRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(UUID.randomUUID().toString());
        roomType.setHotel(hotel);
        updateRoomTypeFromRequest(roomType, request);

        return roomTypeRepository.save(roomType);
    }

    @Transactional
    public RoomType updateRoomType(String roomTypeId, RoomTypeRequest request) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        updateRoomTypeFromRequest(roomType, request);
        return roomTypeRepository.save(roomType);
    }

    private void updateRoomTypeFromRequest(RoomType roomType, RoomTypeRequest request) {
        roomType.setTypeName(request.getTypeName());
        roomType.setPrice(request.getPrice());
        roomType.setTotalRooms(request.getTotalRooms());
    }

    public List<RoomType> getRoomTypesByHotel(String hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return roomTypeRepository.findByHotel(hotel);
    }

    public List<RoomType> getRoomTypesByHotelAndMaxPrice(String hotelId, Integer maxPrice) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return roomTypeRepository.findByHotelAndPriceLessThanEqual(hotel, maxPrice);
    }

    public RoomType getRoomTypeById(String roomTypeId) {
        return roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room type not found"));
    }

    @Transactional
    public void deleteRoomType(String roomTypeId) {
        if (!roomTypeRepository.existsById(roomTypeId)) {
            throw new RuntimeException("Room type not found");
        }
        roomTypeRepository.deleteById(roomTypeId);
    }
} 