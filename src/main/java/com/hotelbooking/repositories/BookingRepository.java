package com.hotelbooking.repositories;

import com.hotelbooking.models.Booking;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Hotel;
import com.hotelbooking.models.RoomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByHotel(Hotel hotel);
    List<Booking> findByRoomType(RoomType roomType);
    
    @Query("SELECT b FROM Booking b WHERE b.hotel = :hotel AND " +
           "((b.checkInDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkoutDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkInDate <= :startDate AND b.checkoutDate >= :endDate))")
    List<Booking> findOverlappingBookings(Hotel hotel, LocalDate startDate, LocalDate endDate);
} 