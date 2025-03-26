package com.hotelbooking.services;

import com.hotelbooking.dto.HotelRequest;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.HotelRepository;
import com.hotelbooking.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public Hotel createHotel(String ownerId, HotelRequest request) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Hotel hotel = new Hotel();
        hotel.setHotelId(UUID.randomUUID().toString());
        hotel.setOwner(owner);
        updateHotelFromRequest(hotel, request);

        return hotelRepository.save(hotel);
    }

    @Transactional
    public Hotel updateHotel(String hotelId, HotelRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        updateHotelFromRequest(hotel, request);
        return hotelRepository.save(hotel);
    }

    private void updateHotelFromRequest(Hotel hotel, HotelRequest request) {
        hotel.setHotelName(request.getHotelName());
        hotel.setDescription(request.getDescription());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setCountry(request.getCountry());
        hotel.setAddress(request.getAddress());
        hotel.setLandmark(request.getLandmark());
        hotel.setHotelEmailId(request.getHotelEmailId());
        hotel.setWifi(request.getWifi());
        hotel.setBreakfast(request.getBreakfast());
        hotel.setSwimmingPool(request.getSwimmingPool());
        hotel.setGym(request.getGym());
        hotel.setBar(request.getBar());
        hotel.setRatings(request.getRatings());
    }

    public List<Hotel> searchHotels(String searchTerm) {
        return hotelRepository.searchHotels(searchTerm);
    }

    public List<Hotel> getHotelsByOwner(String ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return hotelRepository.findByOwner(owner);
    }

    public List<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findByCity(city);
    }

    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Transactional
    public void deleteHotel(String hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new RuntimeException("Hotel not found");
        }
        hotelRepository.deleteById(hotelId);
    }
} 