package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.StatRequestDTO;
import com.qubid.backend.dtos.Response.StatResponseDTO;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Stats;
import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.StatRepository;
import com.qubid.backend.services.StatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    @Override
    public StatResponseDTO createStats(Long playerId, StatRequestDTO request) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));

        if (statRepository.existsByPlayerIdAndCricketFormat(playerId, request.getCricketFormat())) {
            throw new IllegalStateException("Stats already exist for this player and format");
        }

        Stats stats = modelMapper.map(request, Stats.class);
        stats.setPlayer(player);

        Stats saved = statRepository.save(stats);

        return mapToResponse(saved);
    }

    @Override
    public StatResponseDTO updateStats(Long statsId, StatRequestDTO request) {
        Stats existing = statRepository.findById(statsId)
                .orElseThrow(() -> new EntityNotFoundException("Stats not found with id: " + statsId));

        Stats mapped = modelMapper.map(request,Stats.class);

        existing.setCricketFormat(mapped.getCricketFormat());
        existing.setBattingStat(mapped.getBattingStat());
        existing.setBowlingStat(mapped.getBowlingStat());
        existing.setHighestLevel(mapped.getHighestLevel());

        Stats updated = statRepository.save(existing);

        return mapToResponse(updated);
    }

    @Override
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
    public StatResponseDTO getStatsByPlayerAndFormat(Long playerId, CricketFormat format) {
        Stats stats = statRepository.findByPlayerIdAndCricketFormat(playerId, format)
                .orElseThrow(() -> new EntityNotFoundException("Stats not found"));

        return mapToResponse(stats);
    }

    @Override
    public void deleteStats(Long statsId) {
        if (!statRepository.existsById(statsId)) {
            throw new EntityNotFoundException("Stats not found with id: " + statsId);
        }

        statRepository.deleteById(statsId);
    }

    private StatResponseDTO mapToResponse(Stats stats) {

        StatResponseDTO dto = modelMapper.map(stats, StatResponseDTO.class);

        dto.setPlayerId(stats.getPlayer().getId());

        if (stats.getBattingStat() != null &&
                stats.getBattingStat().getBallsPlayed() != null &&
                stats.getBattingStat().getBallsPlayed() > 0) {

            dto.setStrikeRate(
                    (stats.getBattingStat().getRunsScored() * 100.0)
                            / stats.getBattingStat().getBallsPlayed()
            );
        }

        if (stats.getBowlingStat() != null &&
                stats.getBowlingStat().getBallsBowled() != null &&
                stats.getBowlingStat().getBallsBowled() > 0) {

            double overs = stats.getBowlingStat().getBallsBowled() / 6.0;

            dto.setEconomy(
                    stats.getBowlingStat().getRunsConceded() / overs
            );
        }

        return dto;
    }
}
