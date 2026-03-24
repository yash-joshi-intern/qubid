package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.PlayerRequestDTO;
import com.qubid.backend.dtos.Response.PlayerResponseDTO;
import com.qubid.backend.dtos.Response.SkillResponseDTO;
import com.qubid.backend.dtos.Response.TeamResponseDTO;
import com.qubid.backend.dtos.Response.TournamentResponseDTO;

import java.math.BigInteger;
import java.util.List;

public interface PlayerService {

    String addPlayer(PlayerRequestDTO playerRequestDTO);

    String addListOfPlayer(List<PlayerRequestDTO> playerDTOList);

    PlayerResponseDTO updatePlayer(Long playerID, PlayerRequestDTO playerRequestDTO);

    PlayerResponseDTO getPlayerById(Long playerID);

    List<PlayerResponseDTO> getListOfPlayer(List<Long> playerIdList);

    String deletePlayer(Long playerID);

    String deleteListOfPlayer(List<Long> playerIDs);

    //remaining --> Check from here

    List<SkillResponseDTO> getListOfSkillsForPlayerID(Long playerID);

    List<TeamResponseDTO> getListOfTeamForPlayerID(Long playerID);

    List<TournamentResponseDTO> getListOfTournamentForPlayerID(Long playerID);

    String addLivePlayerInTournament(Long playerId, Long tournamentId, BigInteger basePrice);

}
