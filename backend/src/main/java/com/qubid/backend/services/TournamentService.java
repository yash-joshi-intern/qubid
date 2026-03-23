package com.qubid.backend.services;

import com.qubid.backend.entities.Franchise;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;
import com.qubid.backend.entities.Tournament;

import java.util.List;

public interface TournamentService {

    public String addTournament(Tournament tournament);

    public String addListOfTournament(List<Tournament> tournamentList);

    public Tournament getTournament(Long tournamentId);

    public List<Tournament> getListOfTournamentId(List<Long> tournamentIdList);

    public Tournament udpateTournament(Long tournamentId, Tournament tournament);
    //use dto in most of the entity fields

    public String deleteTournament(Long tournamentId);

    public String deleteListOfTournament(List<Long> listOfTournamentId);

    public List<Player> getPlayerListByTournamentId(Long tournamentId);
    //may have to change this based on the tournament entity class

    public List<Franchise> getFranchiseListByTournamentId(Long tournamentId);

    public List<Team> getTeamListByTournamentId(Long tournamentId);

    public String addListOfTeamInTournament(List<Team> teamList,Long tournamentId);
    //here dto for specific response can be created
    public String udpateListOfTeamInTournament(List<Team> teamList, Long tournamentId);

    public String deleteTournamentTeamList(Long tournamentId);
    //will delte from the middle table

    //franchies pending
}

