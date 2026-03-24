package com.qubid.backend.dtos.Request;

import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatRequestDTO {
    @NotNull
    private CricketFormat cricketFormat;

    @NotNull
    private Integer matchesPlayed;

    @Valid
    private BattingStatDTO battingStat;

    @Valid
    private BowlingStatDTO bowlingStat;

    @NotNull
    private HighestLevel highestLevel;
}
