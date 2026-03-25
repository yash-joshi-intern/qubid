package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.request.BasePriceRequestDTO;
import com.qubid.backend.dtos.response.BasePriceResponseDTO;
import com.qubid.backend.entities.BasePrice;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Tournament;
import com.qubid.backend.repository.BasePriceRepository;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.repository.TournamentRepository;
import com.qubid.backend.services.BasePriceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasePriceServiceImpl implements BasePriceService {

    private final BasePriceRepository basePriceRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    private BasePriceResponseDTO toDTO(BasePrice bp) {
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

    @Override
    public BasePriceResponseDTO createBasePrice(BasePriceRequestDTO dto) {
        Player player = playerRepository.findById(dto.getPlayerId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found: " + dto.getPlayerId()));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found: " + dto.getTournamentId()));

        BasePrice basePrice = BasePrice.builder()
                .player(player)
                .tournament(tournament)
                .basePrice(dto.getBasePrice())
                .build();

        return toDTO(basePriceRepository.save(basePrice));
    }

    @Override
    public BasePriceResponseDTO getBasePriceById(Long id) {
        BasePrice bp = basePriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BasePrice not found: " + id));
        return toDTO(bp);
    }

    @Override
    public List<BasePriceResponseDTO> getBasePricesByPlayer(Long playerId) {
        return basePriceRepository.findByPlayer_Id(playerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BasePriceResponseDTO> getBasePricesByTournament(Long tournamentId) {
        return basePriceRepository.findByTournament_Id(tournamentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BasePriceResponseDTO updateBasePrice(Long id, BasePriceRequestDTO dto) {
        BasePrice bp = basePriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BasePrice not found: " + id));

        bp.setBasePrice(dto.getBasePrice());
        bp.setUpdatedAt(Instant.now());

        return toDTO(basePriceRepository.save(bp));
    }

    @Override
    public void deleteBasePrice(Long id) {
        if (!basePriceRepository.existsById(id)) {
            throw new EntityNotFoundException("BasePrice not found: " + id);
        }
        basePriceRepository.deleteById(id);
    }
}
