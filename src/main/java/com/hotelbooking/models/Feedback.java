package com.hotelbooking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
	@Id
    @Column(name = "feedbackid", length = 100)
    private String feedbackId;
	
	@Column(name = "customerName", length = 50, nullable = false)
    private String customerName;
	
	@Column(name = "customerContact", length = 10, nullable = false)
    private String customerContact;
	
	@Column(name = "description", length = 100, nullable = false)
    private String description;

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
