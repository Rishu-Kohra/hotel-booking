package com.hotelbooking.dto;

import lombok.Data;

@Data
public class HotelRequest {
    private String hotelName;
    private String description;
    private String city;
    private String state;
    private String country;
    private String address;
    private String landmark;
    private String hotelEmailId;
    private Boolean wifi;
    private Boolean breakfast;
    private Boolean swimmingPool;
    private Boolean gym;
    private Boolean bar;
    private Integer ratings;
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getHotelEmailId() {
		return hotelEmailId;
	}
	public void setHotelEmailId(String hotelEmailId) {
		this.hotelEmailId = hotelEmailId;
	}
	public Boolean getWifi() {
		return wifi;
	}
	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}
	public Boolean getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Boolean breakfast) {
		this.breakfast = breakfast;
	}
	public Boolean getSwimmingPool() {
		return swimmingPool;
	}
	public void setSwimmingPool(Boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}
	public Boolean getGym() {
		return gym;
	}
	public void setGym(Boolean gym) {
		this.gym = gym;
	}
	public Boolean getBar() {
		return bar;
	}
	public void setBar(Boolean bar) {
		this.bar = bar;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
    
    
} 