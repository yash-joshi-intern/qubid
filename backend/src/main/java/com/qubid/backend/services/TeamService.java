package com.qubid.backend.services;

import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;

import java.util.List;

public interface TeamService {

    public String addTeam(Team team);

    public String addMultipleTeams(List<Team> teamList);

    public Team getTeam(Long teamId);

    public List<Team> getListOfTeam(List<Long> integerList);

    public Team modifyTeam(Long id, Team team);

    public String deleteTeam(Long teamId);

    public Integer getRemainingPlayerInTeam(Long TeamId);

    //player and team services
    public String addOnPlayerInTeam(Long teamId, Player player);

    public String addListOfPlayerInTeam(Long teamId, List<Long> playerList);

    public List<Player> getListOfPlayerForTeam(Long teamId);

    public String deleteListOfPlayerInTeam(List<Long> listOfPlayer);

    //many team can have the franchieses and tournaments to those will have the
    // the seperate service method and the various method for each operations


}
