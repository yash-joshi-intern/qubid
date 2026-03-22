package com.qubid.backend.services;

import com.qubid.backend.dto.request.PlayerRequestDTO;
import com.qubid.backend.dto.response.PlayerResponseDTO;
import com.qubid.backend.entities.*;

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

    public Stats getPlayerState(Long playerIdD);

    public Stats updatePlayerStats(Long playerId, Stats stats);

    public List<BasePrice> getBasePriceForPlayerId(Long playerID);
    //can be add multiple service for the baseprice but it will be inside the baseprice service
    // add, update, delete based on PlayerID

    public List<Skill> getListOfSkillsForPlayerID(Long playerID);
    //here also Skills realted services can be mentioned, but it will be inside the specific skill service where the id of player will
    // be given and after the various operations can be done.
    // add, update, delete based on PlayerID

    public List<Team> getLisOfTeamForPlayerID(Long playerID);
    // same as above

    public List<Tournament> getListOfTournamentForPlayerID(Long playerID);

}
