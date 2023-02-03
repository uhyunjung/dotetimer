package com.dotetimer.repository;

import com.dotetimer.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer>, QueryDSLRepository {
    Optional<StudyGroup> findById(int id);
}
