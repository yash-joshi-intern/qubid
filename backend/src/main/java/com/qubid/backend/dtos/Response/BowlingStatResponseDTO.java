package com.qubid.backend.dtos.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BowlingStatResponseDTO {
    private Integer runsConceded;
    private Integer ballsBowled;
}
