package com.dotetimer.repository;

import com.dotetimer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, QueryDSLRepository {
    Optional<User> findByEmail(String email);
}
