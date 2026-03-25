package com.qubid.backend.services;

import com.qubid.backend.dtos.request.StatRequestDTO;
import com.qubid.backend.dtos.response.StatResponseDTO;
import com.qubid.backend.enums.CricketFormat;

import java.util.List;

public interface StatService {
    StatResponseDTO createStats(Long playerId, StatRequestDTO request);

    StatResponseDTO updateStats(Long statsId, StatRequestDTO request);

    List<StatResponseDTO> getStatsByPlayer(Long playerId);

    StatResponseDTO getStatsByPlayerAndFormat(Long playerId, CricketFormat format);

    void deleteStats(Long statsId);
}
