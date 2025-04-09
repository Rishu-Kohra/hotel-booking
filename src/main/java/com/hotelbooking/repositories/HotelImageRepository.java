package com.hotelbooking.repositories;

import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import com.hotelbooking.models.HotelImage;
 
@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, String> {
 
	Optional<HotelImage> findByHotelId(String hotelId);
	@Query("Select img.url from HotelImage img where img.hotelId = :hotelId")
    List<String> getImages(String hotelId);
}
