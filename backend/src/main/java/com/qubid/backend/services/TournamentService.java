package com.qubid.backend.services;

import com.qubid.backend.dto.request.TournamentRequestDTO;
import com.qubid.backend.dto.response.TournamentResponseDTO;
import com.qubid.backend.entities.Franchise;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;

import java.util.List;

public interface TournamentService {

    String addTournament(TournamentRequestDTO requestDTO);

    String addListOfTournament(List<TournamentRequestDTO> tournamentList);

    TournamentResponseDTO getTournament(Long tournamentId);

    List<TournamentResponseDTO> getListOfTournamentId(List<Long> tournamentIdList);

    TournamentResponseDTO updateTournament(Long tournamentId, TournamentRequestDTO requestDTO);

    String deleteTournament(Long tournamentId);

    String deleteListOfTournament(List<Long> listOfTournamentId);

    // Fetch related entities by tournament
    List<Player> getPlayerListByTournamentId(Long tournamentId);

    List<Franchise> getFranchiseListByTournamentId(Long tournamentId);

    List<Team> getTeamListByTournamentId(Long tournamentId);

    // Team ↔ Tournament operations
    // List<Long> instead of List<Team> — cleaner, consistent with other services
    String addListOfTeamInTournament(Long tournamentId, List<Long> teamIds);

    String updateListOfTeamInTournament(Long tournamentId, List<Long> teamIds);

    String deleteTournamentTeamList(Long tournamentId);
    //will delte from the middle table

    //franchies pending
}

