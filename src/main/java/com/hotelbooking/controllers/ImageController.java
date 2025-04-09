package com.hotelbooking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
 
import com.hotelbooking.services.ImageService;
 
@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
	@Autowired
	private ImageService imageService;
 
	@PostMapping("/hotel/{hotelId}")
    @PreAuthorize("hasRole('OWNER')")
	public void uploadHotelPhoto(@PathVariable String hotelId, @RequestParam MultipartFile file) {
		imageService.uploadHotelPhoto(hotelId, file);
	}
 
	@GetMapping("/hotel/getImage/{hotelId}")
	public ResponseEntity<?> getHotelPhoto(@PathVariable String hotelId) {
		List<String> url = imageService.getHotelPhoto(hotelId);
	    if (url != null) {
	        return ResponseEntity.ok(url);
	    } 
	    else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found for hotelId: " + hotelId);
	    }
 
	}
}
