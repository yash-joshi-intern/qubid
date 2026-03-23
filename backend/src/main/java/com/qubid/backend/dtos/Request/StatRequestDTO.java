package com.qubid.backend.dtos.Request;

import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatRequestDTO {
    private CricketFormat cricketFormat;

    private Integer matchesPlayed;

    private BattingStatDTO battingStat;

    private BowlingStatDTO bowlingStat;

    private HighestLevel highestLevel;
}
