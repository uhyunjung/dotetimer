package com.dotetimer.repository;

import com.dotetimer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer>, QueryDSLRepository {
    Optional<Review> findById(int id);
}
