package com.qubid.backend.repository;

import com.qubid.backend.entities.Stats;
import com.qubid.backend.enums.CricketFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<Stats, Long> {

    Optional<Stats> findByPlayerIdAndCricketFormat(Long playerId, CricketFormat format);

    boolean existsByPlayerIdAndCricketFormat(Long playerId, CricketFormat format);

    List<Stats> findByPlayerId(Long playerId);
}