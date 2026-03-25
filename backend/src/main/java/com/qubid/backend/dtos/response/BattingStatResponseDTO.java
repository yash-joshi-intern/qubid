package com.qubid.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BattingStatResponseDTO {
    private Integer runsScored;
    private Integer ballsPlayed;
}
