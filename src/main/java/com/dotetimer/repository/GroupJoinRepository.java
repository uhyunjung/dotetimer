package com.dotetimer.repository;

import com.dotetimer.domain.GroupJoin;
import org.springframework.data.jpa.repository.JpaRepository;

// save 사용
public interface GroupJoinRepository extends JpaRepository<GroupJoin, Integer>, QueryDSLRepository {
}
