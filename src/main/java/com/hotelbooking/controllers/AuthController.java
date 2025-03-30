package com.hotelbooking.controllers;

import com.hotelbooking.dto.AuthRequest;
import com.hotelbooking.dto.CustomerRegistrationRequest;
import com.hotelbooking.dto.OwnerRegistrationRequest;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.OwnerRepository;
import com.hotelbooking.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        String result = authService.registerCustomer(request);
        return ResponseEntity.ok(createResponse("message", result));
    }

    @PostMapping("/register/owner")
    public ResponseEntity<?> registerOwner(@RequestBody OwnerRegistrationRequest request) {
        String result = authService.registerOwner(request);
        return ResponseEntity.ok(createResponse("message", result));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request);
        
        Customer customer = customerRepository.findByCustomerEmailId(request.getEmail()).orElse(null);
        if (customer != null) {
            return ResponseEntity.ok(createUserResponse(customer.getCustomerId(), customer.getCustomerEmailId(),token, "customer"));
        }

        // Try to find owner
        Owner owner = ownerRepository.findByOwnerEmailId(request.getEmail()).orElse(null);
        if (owner != null) {
            return ResponseEntity.ok(createUserResponse(owner.getOwnerId(), owner.getOwnerEmailId(),token, "owner"));
        }

        
        return ResponseEntity.ok(createResponse("token", token));
    }

    private Map<String, String> createResponse(String key, String value) {
        Map<String, String> response = new HashMap<>();
        response.put(key, value);
        return response;
    }
    private Map<String, String> createUserResponse(String id, String email, String token, String role) {
        Map<String, String> response = new HashMap<>();
        response.put("id", id);
        response.put("email", email);
        response.put("token", token);
        response.put("role", role);

        return response;
    }
} 