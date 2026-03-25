package com.qubid.backend.services;

import com.qubid.backend.dtos.request.TeamRequestDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;

import java.util.List;

public interface TeamService {

    String addTeam(TeamRequestDTO requestDTO);

    String addMultipleTeams(List<TeamRequestDTO> teamList);

    TeamResponseDTO getTeam(Long teamId);

    List<TeamResponseDTO> getListOfTeam(List<Long> teamIds);

    TeamResponseDTO modifyTeam(Long id, TeamRequestDTO requestDTO);

    String deleteTeam(Long teamId);

    Integer getRemainingPlayerSlots(Long teamId);

    // Player ↔ Team operations
    String addOnePlayerInTeam(Long teamId, Long playerId);

    String addListOfPlayerInTeam(Long teamId, List<Long> playerIds);

    List<PlayerResponseDTO> getListOfPlayerForTeam(Long teamId);

    // Fixed signature — teamId is required to know which team
    String deleteListOfPlayerInTeam(Long teamId, List<Long> playerIds);

    //many team can have the franchieses and tournaments to those will have the
    // the seperate service method and the various method for each operations


}
