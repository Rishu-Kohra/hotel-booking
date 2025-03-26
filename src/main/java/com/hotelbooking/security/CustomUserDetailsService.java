package com.hotelbooking.security;

import com.hotelbooking.models.Customer;
import com.hotelbooking.models.Owner;
import com.hotelbooking.repositories.CustomerRepository;
import com.hotelbooking.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find customer
        Customer customer = customerRepository.findByCustomerEmailId(email).orElse(null);
        if (customer != null) {
            return new User(customer.getCustomerEmailId(), customer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        }

        // Try to find owner
        Owner owner = ownerRepository.findByOwnerEmailId(email).orElse(null);
        if (owner != null) {
            return new User(owner.getOwnerEmailId(), owner.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 