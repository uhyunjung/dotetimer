package com.dotetimer.repository;

import com.dotetimer.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer>, QueryDSLRepository {

}
