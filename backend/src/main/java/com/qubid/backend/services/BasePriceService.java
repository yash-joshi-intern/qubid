package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.BasePriceRequestDTO;
import com.qubid.backend.dtos.Response.BasePriceResponseDTO;

import java.util.List;

public interface BasePriceService {

    BasePriceResponseDTO createBasePrice(BasePriceRequestDTO requestDTO);

    BasePriceResponseDTO getBasePriceById(Long id);

    List<BasePriceResponseDTO> getBasePricesByPlayer(Long playerId);

    List<BasePriceResponseDTO> getBasePricesByTournament(Long tournamentId);

    BasePriceResponseDTO updateBasePrice(Long id, BasePriceRequestDTO requestDTO);

    void deleteBasePrice(Long id);
}
