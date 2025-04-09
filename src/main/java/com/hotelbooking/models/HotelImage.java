package com.hotelbooking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Table(name = "hotel_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelImage {
	@Id
    @Column(name = "imageId", length = 100)
    private String imageId;
	@Column(name = "hotelId")
    private String hotelId;
	@Column(name = "url", length = 2048)
    private String url;
 
	public String getImageId() {
		return imageId;
	}
 
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
 
	public String getHotelId() {
		return hotelId;
	}
 
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
 
	public String getUrl() {
		return url;
	}
 
	public void setUrl(String url) {
		this.url = url;
	}
}
