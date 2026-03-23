package com.qubid.backend.dtos.Response;

import com.qubid.backend.dtos.Request.BattingStatDTO;
import com.qubid.backend.dtos.Request.BowlingStatDTO;
import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import lombok.Data;

@Data
public class StatResponseDTO {

    private Long id;

    private Long playerId;

    private CricketFormat cricketFormat;

    private Integer matchesPlayed;

    private BattingStatDTO battingStat;

    private BowlingStatDTO bowlingStat;

    private HighestLevel highestLevel;

    private Double strikeRate;

    private Double economy;

}
