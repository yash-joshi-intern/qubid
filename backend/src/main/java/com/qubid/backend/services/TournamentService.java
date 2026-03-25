package com.qubid.backend.services;

import com.qubid.backend.dtos.request.TournamentRequestDTO;
import com.qubid.backend.dtos.response.FranchiseResponseForTournamentDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.dtos.response.TournamentResponseDTO;

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
    List<PlayerResponseDTO> getPlayerListByTournamentId(Long tournamentId);

    List<FranchiseResponseForTournamentDTO> getFranchiseListByTournamentId(Long tournamentId);  // was List<Franchise>

    List<TeamResponseDTO> getTeamListByTournamentId(Long tournamentId);            // was List<Team>

    // Team ↔ Tournament operations
    // List<Long> instead of List<Team> — cleaner, consistent with other services
    String addListOfTeamInTournament(Long tournamentId, List<Long> teamIds);

    String updateListOfTeamInTournament(Long tournamentId, List<Long> teamIds);

    String deleteTournamentTeamList(Long tournamentId);
    //will delte from the middle table

    //franchies pending
}

