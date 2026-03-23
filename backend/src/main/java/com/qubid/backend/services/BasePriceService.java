package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.BasePriceRequestDTO;
import com.qubid.backend.dtos.Response.BasePriceResponseDTO;

import java.util.List;

public interface BasePriceService {

    // Create a new base price for a player in a tournament
    BasePriceResponseDTO addBasePrice(BasePriceRequestDTO requestDTO);

    // Get a single base price record by its own ID
    BasePriceResponseDTO getBasePriceById(Long basePriceId);

    // Get all base prices set for a specific player (across all tournaments)
    List<BasePriceResponseDTO> getBasePricesByPlayerId(Long playerId);

    // Get all base prices set within a specific tournament (across all players)
    List<BasePriceResponseDTO> getBasePricesByTournamentId(Long tournamentId);

    // Get the base price for a specific player in a specific tournament
    BasePriceResponseDTO getBasePriceByPlayerAndTournament(Long playerId, Long tournamentId);

    // Update an existing base price record
    BasePriceResponseDTO updateBasePrice(Long basePriceId, BasePriceRequestDTO requestDTO);

    // Delete a base price record by its ID
    String deleteBasePrice(Long basePriceId);
}
