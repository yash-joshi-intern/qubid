package com.qubid.backend.services;

import com.qubid.backend.dtos.request.BasePriceRequestDTO;
import com.qubid.backend.dtos.response.BasePriceResponseDTO;

import java.util.List;

public interface BasePriceService {

    BasePriceResponseDTO createBasePrice(BasePriceRequestDTO requestDTO);

    BasePriceResponseDTO getBasePriceById(Long id);

    List<BasePriceResponseDTO> getBasePricesByPlayer(Long playerId);

    List<BasePriceResponseDTO> getBasePricesByTournament(Long tournamentId);

    BasePriceResponseDTO updateBasePrice(Long id, BasePriceRequestDTO requestDTO);

    void deleteBasePrice(Long id);
}
