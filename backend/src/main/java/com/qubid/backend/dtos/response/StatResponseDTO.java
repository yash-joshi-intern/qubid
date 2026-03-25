package com.qubid.backend.dtos.Response;

import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatResponseDTO {

    private Long id;

    private Long playerId;

    private CricketFormat cricketFormat;

    private Integer matchesPlayed;

    private BattingStatResponseDTO battingStat;
    private BowlingStatResponseDTO bowlingStat;

    private HighestLevel highestLevel;

    private Double strikeRate;

    private Double economy;

}
