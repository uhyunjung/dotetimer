package com.dotetimer.repository;

import com.dotetimer.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Integer> {
    Optional<Coin> findByUserIdAndStudiedAt(int userId, LocalDate studiedAt);
}
