package com.hotelbooking.repositories;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    List<Hotel> findByOwner(Owner owner);
    List<Hotel> findByCity(String city);
    List<Hotel> findByState(String state);
    List<Hotel> findByCountry(String country);
    
    @Query("SELECT h FROM Hotel h WHERE " +
           "LOWER(h.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(h.state) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(h.country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(h.hotelName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Hotel> searchHotels(String searchTerm);
} 