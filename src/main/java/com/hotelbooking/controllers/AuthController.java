package com.hotelbooking.controllers;

import com.hotelbooking.dto.AuthRequest;
import com.hotelbooking.dto.CustomerRegistrationRequest;
import com.hotelbooking.dto.OwnerRegistrationRequest;
import com.hotelbooking.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

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
        return ResponseEntity.ok(createResponse("token", token));
    }

    private Map<String, String> createResponse(String key, String value) {
        Map<String, String> response = new HashMap<>();
        response.put(key, value);
        return response;
    }
} 