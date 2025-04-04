package com.hotelbooking.controllers;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.hotelbooking.dto.UserRequest;
import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Owner;
import com.hotelbooking.services.UserService;
 
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Customer> getCustomerProfile(@PathVariable String customerId) {
        return ResponseEntity.ok(userService.getCustomerProfile(customerId));
    }
	
	@PutMapping("/update/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Customer> updateCustomerProfile(@PathVariable String customerId, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateCustomerProfile(customerId, request));
    }
	
	@GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('OWNER')")
	public ResponseEntity<Owner> getOwnerProfile(@PathVariable String ownerId) {
        return ResponseEntity.ok(userService.getOwnerProfile(ownerId));
    }
	
	@PutMapping("/update/owner/{ownerId}")
    @PreAuthorize("hasRole('OWNER')")
	public ResponseEntity<Owner> updateOwnerProfile(@PathVariable String ownerId, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateOwnerProfile(ownerId, request));
    }
}