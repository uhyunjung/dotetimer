package com.dotetimer.repository;

import com.dotetimer.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

// Spring Data JPA에서 JPQL 생성
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer>, QueryDSLRepository {
    Optional<StudyGroup> findById(int id);
}
