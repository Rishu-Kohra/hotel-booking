package com.hotelbooking.dto;
 
import lombok.Data;
 
@Data
public class UserRequest {
 
	private String name;
    private String contact;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
 
}