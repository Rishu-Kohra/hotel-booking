package com.hotelbooking.repositories;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {
    List<RoomType> findByHotel(Hotel hotel);
    List<RoomType> findByHotelAndPriceLessThanEqual(Hotel hotel, Integer maxPrice);
} 