package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.PlayerNotFoundException;
import com.qubid.backend.ExceptionHandler.TeamNotFoundException;
import com.qubid.backend.dtos.request.TeamRequestDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.TeamRepository;
import com.qubid.backend.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String addTeam(TeamRequestDTO requestDTO) {
        //helper method
        validatePlayerSlots(requestDTO.getMinPlayers(), requestDTO.getMaxPlayers());
        Team team = modelMapper.map(requestDTO, Team.class);
        teamRepository.save(team);
        return "Team added successfully";
    }

    //The peek() method in Java streams is an intermediate operation that allows you to perform an action on each element as it flows through the stream pipeline,
    // primarily for debugging and logging purposes, without modifying the stream's elements or structure
    @Override
    @Transactional
    public String addMultipleTeams(List<TeamRequestDTO> teamList) {
        List<Team> teams = teamList.stream()
                .peek(dto -> validatePlayerSlots(dto.getMinPlayers(), dto.getMaxPlayers()))
                .map(dto -> modelMapper.map(dto, Team.class))
                .toList();
        teamRepository.saveAll(teams);
        return "Teams added successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponseDTO getTeam(Long teamId) {
        Team team = findTeamOrThrow(teamId);
        return modelMapper.map(team, TeamResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponseDTO> getListOfTeam(List<Long> teamIds) {
        List<Team> teams = teamRepository.findAllById(teamIds);

        if (teams.isEmpty()) {
            throw new RuntimeException("No teams found for given IDs");
        }

        return teams.stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public TeamResponseDTO modifyTeam(Long id, TeamRequestDTO requestDTO) {
        Team existing = findTeamOrThrow(id);

        validatePlayerSlots(requestDTO.getMinPlayers(), requestDTO.getMaxPlayers());

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        // we have used it in two repos , better to make another config file
        modelMapper.map(requestDTO, existing);
        existing.setUpdatedAt(Instant.now());

        Team saved = teamRepository.save(existing);
        return modelMapper.map(saved, TeamResponseDTO.class);
    }

    @Override
    @Transactional
    public String deleteTeam(Long teamId) {
        Team team = findTeamOrThrow(teamId);

        List<Player> players = team.getPlayers();
        players.forEach(player -> player.getTeams().remove(team));
        playerRepository.saveAll(players);

        teamRepository.delete(team);
        return "Team deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getRemainingPlayerSlots(Long teamId) {
        Team team = findTeamOrThrow(teamId);
        return team.getMaxPlayers() - team.getMinPlayers();
    }

    @Override
    @Transactional
    public String addOnePlayerInTeam(Long teamId, Long playerId) {
        Team team = findTeamOrThrow(teamId);
        Player player = findPlayerOrThrow(playerId);

        if (team.getPlayers().contains(player)) {
            throw new RuntimeException("Player " + playerId + " is already in team " + teamId);
        }

        if (team.getPlayers().size() >= team.getMaxPlayers()) {
            throw new RuntimeException("Team has reached maximum player capacity of " + team.getMaxPlayers());
        }

        player.getTeams().add(team);
        team.getPlayers().add(player);

        playerRepository.save(player);
        //optimized way -> custom query to avoid unnecessary data load
        return "Player added to team successfully";
    }

    @Transactional
    public String addListOfPlayerInTeam(Long teamId, List<Long> playerIds) {
        Team team = findTeamOrThrow(teamId);
        List<Player> playersToAdd = playerRepository.findAllById(playerIds);

        if (playersToAdd.isEmpty()) {
            throw new RuntimeException("No players found for given IDs");
        }

        List<Player> newPlayers = playersToAdd.stream()
                .filter(p -> !team.getPlayers().contains(p))
                .toList();

        if (newPlayers.isEmpty()) {
            return "All players are already in the team";
        }

        int afterCount = team.getPlayers().size() + newPlayers.size();
        if (afterCount > team.getMaxPlayers()) {
            throw new RuntimeException(
                    "Adding these players exceeds max capacity. " +
                            "Current: " + team.getPlayers().size() +
                            ", Trying to add: " + newPlayers.size() +
                            ", Max allowed: " + team.getMaxPlayers()
            );
        }

        newPlayers.forEach(player -> {
            player.getTeams().add(team);
            team.getPlayers().add(player);
        });

        playerRepository.saveAll(newPlayers);
        return "Players added to team successfully. Added: " + newPlayers.size()
                + ", Skipped (already in team): " + (playersToAdd.size() - newPlayers.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getListOfPlayerForTeam(Long teamId) {
        Team team = findTeamOrThrow(teamId);
        return team.getPlayers().stream()
                .map(dto -> modelMapper.map(dto, PlayerResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public String deleteListOfPlayerInTeam(Long teamId, List<Long> playerIds) {
        Team team = findTeamOrThrow(teamId);

        List<Player> playersToRemove = playerRepository.findAllById(playerIds);

        if (playersToRemove.isEmpty()) {
            throw new RuntimeException("None of the given players found");
        }

        boolean anyRemoved = false;
        for (Player player : playersToRemove) {
            if (player.getTeams().contains(team)) {
                player.getTeams().remove(team);
                anyRemoved = true;
            }
        }

        if (!anyRemoved) {
            throw new RuntimeException("None of the given players belong to team " + teamId);
        }

        playerRepository.saveAll(playersToRemove);
        return "Players removed from team successfully";
    }

    //My helpers Methods

    private Team findTeamOrThrow(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
    }

    private Player findPlayerOrThrow(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    private void validatePlayerSlots(Integer min, Integer max) {
        if (min != null && max != null && min > max) {
            throw new RuntimeException("minPlayers (" + min + ") cannot be greater than maxPlayers (" + max + ")");
        }
    }
}
