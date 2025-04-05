package com.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelbooking.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
	
}
