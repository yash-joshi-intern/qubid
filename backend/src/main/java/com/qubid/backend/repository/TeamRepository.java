package com.qubid.backend.repository;

import com.qubid.backend.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByFranchiseIdAndTournamentId(Long franchiseId, Long tournamentId);
}
