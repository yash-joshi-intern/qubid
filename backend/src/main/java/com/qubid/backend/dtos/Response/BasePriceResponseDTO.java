package com.qubid.backend.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePriceResponseDTO {

    private Long id;


    private Long playerId;
    private String playerFirstName;
    private String playerLastName;


    private Long tournamentId;
    private String tournamentName;

    private BigDecimal basePrice;

    private Instant createdAt;
    private Instant updatedAt;
}
