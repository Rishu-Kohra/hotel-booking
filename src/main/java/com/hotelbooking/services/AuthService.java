package com.hotelbooking.services;

import com.hotelbooking.dto.AuthRequest;
import com.hotelbooking.dto.CustomerRegistrationRequest;
import com.hotelbooking.dto.OwnerRegistrationRequest;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.OwnerRepository;
import com.hotelbooking.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String registerCustomer(CustomerRegistrationRequest request) {
        if (customerRepository.existsByCustomerEmailId(request.getCustomerEmailId())) {
            throw new RuntimeException("Email already registered");
        }

        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID().toString());
//        String customerId = UUID.randomUUID().toString();
//        customer.setCustomerId(customerId);
//        System.out.println("Generated Customer ID: " + customerId);
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerEmailId(request.getCustomerEmailId());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setCustomerContact(request.getCustomerContact());

        customerRepository.save(customer);
        return "Customer registered successfully";
    }

    public String registerOwner(OwnerRegistrationRequest request) {
        if (ownerRepository.existsByOwnerEmailId(request.getOwnerEmailId())) {
            throw new RuntimeException("Email already registered");
        }

        Owner owner = new Owner();
        owner.setOwnerId(UUID.randomUUID().toString());
        owner.setOwnerName(request.getOwnerName());
        owner.setOwnerEmailId(request.getOwnerEmailId());
        owner.setPassword(passwordEncoder.encode(request.getPassword()));
        owner.setOwnerContact(request.getOwnerContact());

        ownerRepository.save(owner);
        return "Owner registered successfully";
    }

    public String authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority().substring(5);
        return jwtTokenUtil.generateToken(userDetails, role);
    }
} 