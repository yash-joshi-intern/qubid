package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.BasePriceRequestDTO;
import com.qubid.backend.dtos.Response.BasePriceResponseDTO;
import com.qubid.backend.entities.BasePrice;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Tournament;
import com.qubid.backend.repository.BasePriceRepository;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.TournamentRepository;
import com.qubid.backend.services.BasePriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasePriceServiceImpl implements BasePriceService {

    private final BasePriceRepository basePriceRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    public BasePriceServiceImpl(BasePriceRepository basePriceRepository,
                                PlayerRepository playerRepository,
                                TournamentRepository tournamentRepository) {
        this.basePriceRepository = basePriceRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private Player fetchPlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + playerId));
    }

    private Tournament fetchTournament(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id: " + tournamentId));
    }

    private BasePrice fetchBasePrice(Long basePriceId) {
        return basePriceRepository.findById(basePriceId)
                .orElseThrow(() -> new RuntimeException("BasePrice not found with id: " + basePriceId));
    }

    /* Maps a BasePrice entity -> BasePriceResponseDTO */
    private BasePriceResponseDTO toResponseDTO(BasePrice bp) {
        return BasePriceResponseDTO.builder()
                .id(bp.getId())
                .playerId(bp.getPlayer().getId())
                .playerFirstName(bp.getPlayer().getFirstName())
                .playerLastName(bp.getPlayer().getLastName())
                .tournamentId(bp.getTournament().getId())
                .tournamentName(bp.getTournament().getName())
                .basePrice(bp.getBasePrice())
                .createdAt(bp.getCreatedAt())
                .updatedAt(bp.getUpdatedAt())
                .build();
    }

    // ─── Service Methods ─────────────────────────────────────────────────────

    @Override
    @Transactional
    public BasePriceResponseDTO addBasePrice(BasePriceRequestDTO requestDTO) {
        // prevent duplicate base price for the same player + tournament
        if (basePriceRepository.existsByPlayerIdAndTournamentId(
                requestDTO.getPlayerId(), requestDTO.getTournamentId())) {
            throw new RuntimeException(
                    "BasePrice already exists for playerId=" + requestDTO.getPlayerId()
                    + " and tournamentId=" + requestDTO.getTournamentId());
        }

        Player player = fetchPlayer(requestDTO.getPlayerId());
        Tournament tournament = fetchTournament(requestDTO.getTournamentId());

        BasePrice basePrice = new BasePrice();
        basePrice.setPlayer(player);
        basePrice.setTournament(tournament);
        basePrice.setBasePrice(requestDTO.getBasePrice());

        BasePrice saved = basePriceRepository.save(basePrice);
        return toResponseDTO(saved);
    }

    @Override
    public BasePriceResponseDTO getBasePriceById(Long basePriceId) {
        return toResponseDTO(fetchBasePrice(basePriceId));
    }

    @Override
    public List<BasePriceResponseDTO> getBasePricesByPlayerId(Long playerId) {
        // validate player exists first
        fetchPlayer(playerId);
        return basePriceRepository.findByPlayerId(playerId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BasePriceResponseDTO> getBasePricesByTournamentId(Long tournamentId) {
        // validate tournament exists first
        fetchTournament(tournamentId);
        return basePriceRepository.findByTournamentId(tournamentId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BasePriceResponseDTO getBasePriceByPlayerAndTournament(Long playerId, Long tournamentId) {
        BasePrice bp = basePriceRepository.findByPlayerIdAndTournamentId(playerId, tournamentId);
        if (bp == null) {
            throw new RuntimeException(
                    "No BasePrice found for playerId=" + playerId + " and tournamentId=" + tournamentId);
        }
        return toResponseDTO(bp);
    }

    @Override
    @Transactional
    public BasePriceResponseDTO updateBasePrice(Long basePriceId, BasePriceRequestDTO requestDTO) {
        BasePrice existing = fetchBasePrice(basePriceId);

        // if player or tournament are being changed, re-validate them
        if (!existing.getPlayer().getId().equals(requestDTO.getPlayerId())) {
            existing.setPlayer(fetchPlayer(requestDTO.getPlayerId()));
        }
        if (!existing.getTournament().getId().equals(requestDTO.getTournamentId())) {
            existing.setTournament(fetchTournament(requestDTO.getTournamentId()));
        }

        existing.setBasePrice(requestDTO.getBasePrice());
        existing.setUpdatedAt(Instant.now());

        BasePrice updated = basePriceRepository.save(existing);
        return toResponseDTO(updated);
    }

    @Override
    @Transactional
    public String deleteBasePrice(Long basePriceId) {
        if (!basePriceRepository.existsById(basePriceId)) {
            throw new RuntimeException("BasePrice not found with id: " + basePriceId);
        }
        basePriceRepository.deleteById(basePriceId);
        return "BasePrice with id " + basePriceId + " deleted successfully";
    }
}
