package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.PlayerNotFoundException;
import com.qubid.backend.ExceptionHandler.TeamNotFoundException;
import com.qubid.backend.dto.request.TeamRequestDTO;
import com.qubid.backend.dto.response.TeamResponseDTO;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.TeamRepository;
import com.qubid.backend.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

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
        List<Team> teams = teamRepository.findAllByIds(teamIds);

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

        team.getPlayers().add(player);
        teamRepository.save(team);
        //optimized way -> custom query to avoid unnecessary data load
        return "Player added to team successfully";
    }

    @Override
    @Transactional
    public String addListOfPlayerInTeam(Long teamId, List<Long> playerIds) {
        Team team = findTeamOrThrow(teamId);
        List<Player> playersToAdd = playerRepository.findAllByIds(playerIds);

        if (playersToAdd.isEmpty()) {
            throw new RuntimeException("No players found for given IDs");
        }

        // Check capacity before adding
        int afterCount = team.getPlayers().size() + playersToAdd.size();
        if (afterCount > team.getMaxPlayers()) {
            throw new RuntimeException(
                    "Adding these players exceeds max capacity. " +
                            "Current: " + team.getPlayers().size() +
                            ", Trying to add: " + playersToAdd.size() +
                            ", Max allowed: " + team.getMaxPlayers()
            );
        }

        // Filter out already existing players
        playersToAdd.stream()
                .filter(p -> !team.getPlayers().contains(p))
                .forEach(team.getPlayers()::add);

        teamRepository.save(team);
        return "Players added to team successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> getListOfPlayerForTeam(Long teamId) {
        Team team = findTeamOrThrow(teamId);
        return team.getPlayers();
    }
    // ^^^^^^
    // have to map with dto --> PlayerResponseDTO

    @Override
    @Transactional
    public String deleteListOfPlayerInTeam(Long teamId, List<Long> playerIds) {
        Team team = findTeamOrThrow(teamId);

        // Only remove players that are actually in this team
        boolean removed = team.getPlayers()
                .removeIf(player -> playerIds.contains(player.getId()));
        // boolean removeIf(Predicate<? super E> filter) --> loop over each element

        if (!removed) {
            throw new RuntimeException("None of the given players belong to team " + teamId);
        }

        teamRepository.save(team);
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
