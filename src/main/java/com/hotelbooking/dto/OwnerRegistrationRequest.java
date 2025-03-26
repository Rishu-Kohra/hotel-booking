package com.hotelbooking.dto;

import lombok.Data;

@Data
public class OwnerRegistrationRequest {
    private String ownerName;
    private String ownerEmailId;
    private String password;
    private String ownerContact;
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