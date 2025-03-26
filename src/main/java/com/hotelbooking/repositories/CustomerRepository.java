package com.hotelbooking.repositories;

import com.hotelbooking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerEmailId(String email);
    boolean existsByCustomerEmailId(String email);
} 