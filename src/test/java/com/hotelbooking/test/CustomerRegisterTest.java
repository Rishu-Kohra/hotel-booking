package com.hotelbooking.test;


import com.hotelbooking.dto.CustomerRegistrationRequest;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.services.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class CustomerRegisterTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCustomer() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setCustomerName("John Doe");
        request.setCustomerEmailId("john.doe@example.com");
        request.setPassword("password");
        request.setCustomerContact("1234567890");

        given(customerRepository.existsByCustomerEmailId(request.getCustomerEmailId())).willReturn(false);
        given(passwordEncoder.encode(request.getPassword())).willReturn("encodedPassword");

        String result = authService.registerCustomer(request);

        assertEquals("Customer registered successfully", result);
    }
    
}
