package com.hotelbooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @Column(name = "inventoryId", length = 100)
    private String inventoryId;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "availableRooms")
    private Integer availableRooms;

    @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "roomTypeId", "hotelId"}))
    private static class DataRoomTypeConstraint {}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(Integer availableRooms) {
		this.availableRooms = availableRooms;
	}
    
    
} 