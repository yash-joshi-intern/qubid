package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.BattingStatDTO;
import com.qubid.backend.dtos.Request.BowlingStatDTO;
import com.qubid.backend.dtos.Request.StatRequestDTO;
import com.qubid.backend.dtos.Response.BattingStatResponseDTO;
import com.qubid.backend.dtos.Response.BowlingStatResponseDTO;
import com.qubid.backend.dtos.Response.StatResponseDTO;
import com.qubid.backend.entities.BattingStat;
import com.qubid.backend.entities.BowlingStat;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Stats;
import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.StatRepository;
import com.qubid.backend.services.StatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public StatResponseDTO createStats(Long playerId, StatRequestDTO request) {

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Player not found with id: " + playerId)
                );

        if (statRepository.existsByPlayerIdAndCricketFormat(playerId, request.getCricketFormat())) {
            throw new IllegalStateException("Stats already exist for this player and format");
        }

        Stats stats = Stats.builder()
                .player(player)
                .cricketFormat(request.getCricketFormat())
                .battingStat(mapBattingStat(request.getBattingStat()))
                .bowlingStat(mapBowlingStat(request.getBowlingStat()))
                .highestLevel(request.getHighestLevel())
                .build();

        Stats saved = statRepository.save(stats);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public StatResponseDTO updateStats(Long statsId, StatRequestDTO request) {

        Stats existing = statRepository.findById(statsId)
                .orElseThrow(() -> new EntityNotFoundException("Stats not found with id: " + statsId));

        existing.setCricketFormat(request.getCricketFormat());
        existing.setBattingStat(mapBattingStat(request.getBattingStat()));
        existing.setBowlingStat(mapBowlingStat(request.getBowlingStat()));
        existing.setHighestLevel(request.getHighestLevel());

        Stats updated = statRepository.save(existing);

        return mapToResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatResponseDTO> getStatsByPlayer(Long playerId) {

        if (!playerRepository.existsById(playerId)) {
            throw new EntityNotFoundException("Player not found with id: " + playerId);
        }

        return statRepository.findByPlayerId(playerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StatResponseDTO getStatsByPlayerAndFormat(Long playerId, CricketFormat format) {

        Stats stats = statRepository.findByPlayerIdAndCricketFormat(playerId, format)
                .orElseThrow(() -> new EntityNotFoundException("Stats not found"));

        return mapToResponse(stats);
    }

    @Override
    @Transactional
    public void deleteStats(Long statsId) {

        if (!statRepository.existsById(statsId)) {
            throw new EntityNotFoundException("Stats not found with id: " + statsId);
        }

        statRepository.deleteById(statsId);
    }

    private BattingStat mapBattingStat(BattingStatDTO dto) {
        if (dto == null) return null;

        return BattingStat.builder()
                .runsScored(dto.getRunsScored())
                .ballsPlayed(dto.getBallsPlayed())
                .build();
    }

    private BowlingStat mapBowlingStat(BowlingStatDTO dto) {
        if (dto == null) return null;

        return BowlingStat.builder()
                .runsConceded(dto.getRunsConceded())
                .ballsBowled(dto.getBallsBowled())
                .build();
    }

    private BowlingStatResponseDTO mapBowlingToResponse(BowlingStat stat) {
        if (stat == null) return null;

        return BowlingStatResponseDTO.builder()
                .runsConceded(stat.getRunsConceded())
                .ballsBowled(stat.getBallsBowled())
                .build();
    }

    private BattingStatResponseDTO mapBattingToResponse(BattingStat stat) {
        if (stat == null) return null;

        return BattingStatResponseDTO.builder()
                .runsScored(stat.getRunsScored())
                .ballsPlayed(stat.getBallsPlayed())
                .build();
    }

    private StatResponseDTO mapToResponse(Stats stats) {

        Double strikeRate = null;
        Double economy = null;

        if (stats.getBattingStat() != null &&
                stats.getBattingStat().getBallsPlayed() != null &&
                stats.getBattingStat().getBallsPlayed() > 0) {
            strikeRate = (stats.getBattingStat().getRunsScored() * 100.0)
                    / stats.getBattingStat().getBallsPlayed();
        }

        if (stats.getBowlingStat() != null &&
                stats.getBowlingStat().getBallsBowled() != null &&
                stats.getBowlingStat().getBallsBowled() > 0) {

            double overs = stats.getBowlingStat().getBallsBowled() / 6.0;

            economy = stats.getBowlingStat().getRunsConceded() / overs;
        }

        return StatResponseDTO.builder()
                .id(stats.getId()) // you missed this earlier
                .playerId(stats.getPlayer().getId())
                .cricketFormat(stats.getCricketFormat())
                .highestLevel(stats.getHighestLevel())
                .battingStat(mapBattingToResponse(stats.getBattingStat()))
                .bowlingStat(mapBowlingToResponse(stats.getBowlingStat()))
                .strikeRate(strikeRate)
                .economy(economy)
                .build();
    }
}