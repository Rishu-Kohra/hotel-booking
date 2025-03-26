package com.hotelbooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @Column(name = "hotelId", length = 100)
    private String hotelId;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private Owner owner;

    @Column(name = "hotelName", length = 50, nullable = false)
    private String hotelName;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "city", length = 20, nullable = false)
    private String city;

    @Column(name = "state", length = 20, nullable = false)
    private String state;

    @Column(name = "country", length = 20, nullable = false)
    private String country;

    @Column(name = "address", length = 28, nullable = false)
    private String address;

    @Column(name = "landmark", length = 20)
    private String landmark;

    @Column(name = "hotelEmailId", length = 50, nullable = false)
    private String hotelEmailId;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "breakfast")
    private Boolean breakfast;

    @Column(name = "swimming_Pool")
    private Boolean swimmingPool;

    @Column(name = "gym")
    private Boolean gym;

    @Column(name = "bar")
    private Boolean bar;

    @Column(name = "ratings", nullable = false)
    @Max(value = 7, message = "Rating cannot be more than 7")
    private Integer ratings;

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

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