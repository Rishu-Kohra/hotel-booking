package com.hotelbooking.services;
 
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.dto.FeedbackRequest;
import com.hotelbooking.dto.UserRequest;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Feedback;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.FeedbackRepository;
import com.hotelbooking.repositories.OwnerRepository;
 
@Service
public class UserService {
	@Autowired
    private CustomerRepository customerRepository;
 
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    public Customer getCustomerProfile(String customerId) {
    	Customer customer = customerRepository.findById(customerId)
    			.orElseThrow(() -> new RuntimeException("Customer not found"));
    	Customer newCustomer = new Customer();
    	newCustomer.setCustomerId(customer.getCustomerId());
    	newCustomer.setCustomerName(customer.getCustomerName());
    	newCustomer.setCustomerEmailId(customer.getCustomerEmailId());
    	newCustomer.setCustomerContact(customer.getCustomerContact());
		return newCustomer;
    }
    public Owner getOwnerProfile(String ownerId) {
    	Owner owner = ownerRepository.findById(ownerId)
    			.orElseThrow(() -> new RuntimeException("Owner not found"));
    	Owner newOwner = new Owner();
    	newOwner.setOwnerId(owner.getOwnerId());
    	newOwner.setOwnerName(owner.getOwnerName());
    	newOwner.setOwnerEmailId(owner.getOwnerEmailId());
    	newOwner.setOwnerContact(owner.getOwnerContact());
		return newOwner;
    }
    @Transactional
    public Customer updateCustomerProfile(String customerId, UserRequest request) {
    	Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
 
    	updateCustomerFromRequest(existingCustomer, request);
    	return customerRepository.save(existingCustomer);
    }
    private void updateCustomerFromRequest(Customer existingCustomer, UserRequest request) {
    	existingCustomer.setCustomerName(request.getName());
    	existingCustomer.setCustomerContact(request.getContact());
    }
    @Transactional
    public Owner updateOwnerProfile(String ownerId, UserRequest request) {
    	Owner existingOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
 
    	updateOwnerFromRequest(existingOwner, request);
    	return ownerRepository.save(existingOwner);
    }
    private void updateOwnerFromRequest(Owner existingOwner, UserRequest request) {
    	existingOwner.setOwnerName(request.getName());
    	existingOwner.setOwnerContact(request.getContact());
    }
    
    @Transactional
    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }
    
    @Transactional
    public void feedbackCustomer(String customerId, FeedbackRequest feedbackRequest) {
    	Feedback feedback = new Feedback();
    	feedback.setFeedbackId(UUID.randomUUID().toString());
    	feedback.setCustomerName(feedbackRequest.getName());
    	feedback.setCustomerContact(feedbackRequest.getContact());
    	feedback.setDescription(feedbackRequest.getDescription());
    	feedbackRepository.save(feedback);
    }
}