package com.dotetimer.repository;

import com.dotetimer.domain.Donate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonateRepository extends JpaRepository<Donate, Integer> {
}
