package com.qubid.backend.repository;

import com.qubid.backend.entities.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice, Long> {

    // All base prices set for a specific player (across all tournaments)
    List<BasePrice> findByPlayerId(Long playerId);

    // All base prices set for a specific tournament (across all players)
    List<BasePrice> findByTournamentId(Long tournamentId);

    // Check if a base price already exists for a given player + tournament pair
    boolean existsByPlayerIdAndTournamentId(Long playerId, Long tournamentId);

    // Fetch the specific base price record for a player in a tournament
    BasePrice findByPlayerIdAndTournamentId(Long playerId, Long tournamentId);
}
