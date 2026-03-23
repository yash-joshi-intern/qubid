package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.PlayerNotFoundException;
import com.qubid.backend.dto.request.PlayerRequestDTO;
import com.qubid.backend.dto.response.PlayerResponseDTO;
import com.qubid.backend.entities.*;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    @Override
    public String addPlayer(PlayerRequestDTO playerRequestDTO) {
        Player player = modelMapper.map(playerRequestDTO, Player.class);
        playerRepository.save(player);
        return "Player Saved Successfully!";
    }

    @Override
    @Transactional
    public String addListOfPlayer(List<PlayerRequestDTO> playerDTOList) {
        List<Player> players = playerDTOList.stream()
                .map(dto -> modelMapper.map(dto, Player.class))
                .toList();
        playerRepository.saveAll(players);
        return "Player List added successfully";
    }

    @Override
    @Transactional
    public PlayerResponseDTO updatePlayer(Long playerID, PlayerRequestDTO playerRequestDTO) {
        Player existing = playerRepository.findById(playerID)
                .orElseThrow(() -> new PlayerNotFoundException(playerID));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(playerRequestDTO, existing);

        existing.setUpdatedAt(Instant.now());

        Player player = playerRepository.save(existing);

        return modelMapper.map(player, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerResponseDTO getPlayerById(Long playerID) {
        Player player = playerRepository.findById(playerID)
                .orElseThrow(() -> new PlayerNotFoundException(playerID));
        return modelMapper.map(player, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getListOfPlayer(List<Long> playerIdList) {
        List<Player> players = playerRepository.findAllByIds(playerIdList);

        if (players.isEmpty()) {
            throw new RuntimeException("No Players found by given list of IDs");
        }

        return players.stream()
                .map(player -> modelMapper.map(player, PlayerResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public String deletePlayer(Long playerID) {
        Player player = playerRepository.findById(playerID)
                .orElseThrow(() -> new PlayerNotFoundException(playerID));
        playerRepository.delete(player);
        return "Player deleted successfully";
    }

    @Override
    @Transactional
    public String deleteListOfPlayer(List<Long> playerIDs) {
        List<Player> players = playerRepository.findAllByIds(playerIDs);

        if (players.isEmpty()) {
            throw new RuntimeException("No players found for given IDs");
        }

        playerRepository.deleteAll(players);
        return "Players deleted successfully";
    }

    @Override
    public Stats getPlayerState(Long playerIdD) {
        return null;
    }

    @Override
    public Stats updatePlayerStats(Long playerId, Stats stats) {
        return null;
    }

    @Override
    public List<BasePrice> getBasePriceForPlayerId(Long playerID) {
        return List.of();
    }

    @Override
    public List<Skill> getListOfSkillsForPlayerID(Long playerID) {
        return List.of();
    }

    @Override
    public List<Team> getLisOfTeamForPlayerID(Long playerID) {
        return List.of();
    }

    @Override
    public List<Tournament> getListOfTournamentForPlayerID(Long playerID) {
        return List.of();
    }
}
