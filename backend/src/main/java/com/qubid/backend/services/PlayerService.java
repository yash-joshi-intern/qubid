package com.qubid.backend.services;

import com.qubid.backend.entities.*;

import java.util.List;

public interface PlayerService {

    public String addPlayer(Player player);

    public String addListOfPlayer(List<Player> playerList);

    public Player updatePlayer(Long playerID, Player player);

    public Player getPlayerById(Long playerID);

    public List<Player> getListOfPlayer(List<Long> playerIdList);

    public String deletePlayer(Long playerID);

    public String deleteListOfPlayer(List<Long> playerID);

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
