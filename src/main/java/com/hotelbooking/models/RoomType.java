package com.hotelbooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roomTypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @Column(name = "roomTypeId", length = 100)
    private String roomTypeId;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @Column(name = "typeName", length = 50)
    private String typeName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "totalRooms")
    private Integer totalRooms;

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(Integer totalRooms) {
		this.totalRooms = totalRooms;
	}
    
    
} 