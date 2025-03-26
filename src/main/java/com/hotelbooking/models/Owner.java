package com.hotelbooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "owner")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    @Id
    @Column(name = "ownerId", length = 100)
    private String ownerId;

    @Column(name = "ownerName", length = 50, nullable = false)
    private String ownerName;

    @Column(name = "ownerEmailId", length = 50, nullable = false, unique = true)
    private String ownerEmailId;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "ownerContact", length = 10, nullable = false)
    private String ownerContact;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmailId() {
		return ownerEmailId;
	}

	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}
    
} 