package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.PlayerNotFoundException;
import com.qubid.backend.ExceptionHandler.TournamentNotFoundException;
import com.qubid.backend.dtos.request.PlayerRequestDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.SkillResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.dtos.response.TournamentResponseDTO;
import com.qubid.backend.entities.*;
import com.qubid.backend.repository.*;
import com.qubid.backend.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final TournamentRepository tournamentRepository;
    private final AuctionRepository auctionRepository;
    private final BasePriceRepository basePriceRepository;
    private final AuctionPlayerRepository auctionPlayerRepository;

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

        Player saved = playerRepository.save(existing);
        return modelMapper.map(saved, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerResponseDTO getPlayerById(Long playerID) {
        Player player = findPlayerOrThrow(playerID);
        return modelMapper.map(player, PlayerResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getListOfPlayer(List<Long> playerIdList) {
        List<Player> players = playerRepository.findAllById(playerIdList);

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
        Player player = findPlayerOrThrow(playerID);
        playerRepository.delete(player);
        return "Player deleted successfully";
    }

    @Override
    @Transactional
    public String deleteListOfPlayer(List<Long> playerIDs) {
        List<Player> players = playerRepository.findAllById(playerIDs);

        if (players.isEmpty()) {
            throw new RuntimeException("No players found for given IDs");
        }

        playerRepository.deleteAll(players);
        return "Players deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDTO> getListOfSkillsForPlayerID(Long playerID) {
        Player player = findPlayerOrThrow(playerID);

        if (player.getSkills().isEmpty()) {
            throw new RuntimeException("No skills found for player ID: " + playerID);
        }

        return player.getSkills().stream()
                .map(skill -> modelMapper.map(skill, SkillResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponseDTO> getListOfTeamForPlayerID(Long playerID) {
        Player player = findPlayerOrThrow(playerID);

        if (player.getTeams().isEmpty()) {
            throw new RuntimeException("No teams found for player ID: " + playerID);
        }

        return player.getTeams().stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentResponseDTO> getListOfTournamentForPlayerID(Long playerID) {
        Player player = findPlayerOrThrow(playerID);

        if (player.getTournaments().isEmpty()) {
            throw new RuntimeException("No tournaments found for player ID: " + playerID);
        }

        return player.getTournaments().stream()
                .map(tournament -> modelMapper.map(tournament, TournamentResponseDTO.class))
                .toList();
    }


    @Override
    @Transactional
    public String addLivePlayerInTournament(Long playerId, Long tournamentId, BigInteger basePrice) {

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentId));

        AuctionPlayer auctionPlayer = new AuctionPlayer();

        Auction auction = auctionRepository.findAllByTournamentId(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentId));

        BasePrice basePriceObj = BasePrice.builder()
                .basePrice(basePrice)
                .player(player)
                .tournament(tournament)
                .build();

        basePriceRepository.save(basePriceObj);

        auctionPlayer.setBasePrice(basePriceObj);
        auctionPlayer.setPlayer(player);
        auctionPlayer.getBasePrice().setBasePrice(basePrice);
        auctionPlayer.setAuction(auction);

        auctionPlayerRepository.save(auctionPlayer);

        return "Auction Player Added Successfully";
    }

    //helper
    private Player findPlayerOrThrow(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }
}