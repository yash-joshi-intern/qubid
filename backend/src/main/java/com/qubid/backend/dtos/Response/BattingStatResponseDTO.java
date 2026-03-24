package com.qubid.backend.dtos.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BattingStatResponseDTO {
    private Integer runsScored;
    private Integer ballsPlayed;
}
