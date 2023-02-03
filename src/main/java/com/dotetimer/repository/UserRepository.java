package com.dotetimer.repository;

import com.dotetimer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring Data JPA에서 JPQL 생성
public interface UserRepository extends JpaRepository<User, Integer>, QueryDSLRepository {
    Optional<User> findByEmail(String email);
}
