package com.hotelbooking.repositories;

import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.Inventory;
import com.hotelbooking.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    List<Inventory> findByHotelAndDate(Hotel hotel, LocalDate date);
    List<Inventory> findByRoomTypeAndDateBetween(RoomType roomType, LocalDate startDate, LocalDate endDate);
    Optional<Inventory> findByHotelAndRoomTypeAndDate(Hotel hotel, RoomType roomType, LocalDate date);
    
    @Query("SELECT i FROM Inventory i WHERE i.hotel = :hotel AND i.roomType = :roomType " +
           "AND i.date BETWEEN :startDate AND :endDate AND i.date != :endDate AND i.availableRooms >= :requiredRooms")
    List<Inventory> findAvailableRooms(Hotel hotel, RoomType roomType, LocalDate startDate, 
                                     LocalDate endDate, Integer requiredRooms);
} 