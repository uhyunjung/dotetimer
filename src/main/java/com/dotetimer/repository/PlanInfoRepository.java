package com.dotetimer.repository;

import com.dotetimer.domain.PlanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanInfoRepository extends JpaRepository<PlanInfo, Integer> {
}
