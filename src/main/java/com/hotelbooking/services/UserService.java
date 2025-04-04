package com.hotelbooking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.OwnerRepository;

@Service
public class UserService {
	@Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;
    
    public Customer getCustomerProfile(String customerId) {
    	Customer customer = customerRepository.findById(customerId).orElse(null);
    	Customer newCustomer = new Customer();
    	newCustomer.setCustomerId(customer.getCustomerId());
    	newCustomer.setCustomerName(customer.getCustomerName());
    	newCustomer.setCustomerEmailId(customer.getCustomerEmailId());
    	newCustomer.setCustomerContact(customer.getCustomerContact());
		return newCustomer;
    }
    
    public Owner getOwnerProfile(String ownerId) {
    	Owner owner = ownerRepository.findById(ownerId).orElse(null);
    	Owner newOwner = new Owner();
    	newOwner.setOwnerId(owner.getOwnerId());
    	newOwner.setOwnerName(owner.getOwnerName());
    	newOwner.setOwnerEmailId(owner.getOwnerEmailId());
    	newOwner.setOwnerContact(owner.getOwnerContact());
		return newOwner;
    }
}
