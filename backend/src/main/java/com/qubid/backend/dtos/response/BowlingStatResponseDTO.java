package com.qubid.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BowlingStatResponseDTO {
    private Integer runsConceded;
    private Integer ballsBowled;
}
