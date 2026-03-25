package com.qubid.backend.dtos.request;

import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatRequestDTO {

    @NotNull(message = "Cricket format must not be null")
    private CricketFormat cricketFormat;

    @NotNull(message = "Matches played must not be null")
    @Min(value = 0, message = "Matches played cannot be negative")
    private Integer matchesPlayed;

    @Valid
    private com.qubid.backend.dtos.request.BattingStatDTO battingStat;

    @Valid
    private BowlingStatDTO bowlingStat;

    @NotNull(message = "Highest level must not be null")
    private HighestLevel highestLevel;
}
