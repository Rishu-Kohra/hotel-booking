package com.hotelbooking.repositories;

import com.hotelbooking.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findByOwnerEmailId(String email);
    boolean existsByOwnerEmailId(String email);
} 