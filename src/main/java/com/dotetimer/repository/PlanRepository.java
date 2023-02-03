package com.dotetimer.repository;

import com.dotetimer.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer>, QueryDSLRepository {
    Optional<Plan> findByIdAndRecorded(int userId, boolean recorded);
}
