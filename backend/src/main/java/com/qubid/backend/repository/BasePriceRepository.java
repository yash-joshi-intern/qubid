package com.qubid.backend.repository;

import com.qubid.backend.entities.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {

    List<BasePrice> findByPlayer_Id(Long playerId);

    List<BasePrice> findByTournament_Id(Long tournamentId);

    Optional<BasePrice> findByPlayer_IdAndTournament_Id(Long playerId, Long tournamentId);
}
