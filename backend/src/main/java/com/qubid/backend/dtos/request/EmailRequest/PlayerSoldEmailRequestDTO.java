package com.qubid.backend.dtos.request.EmailRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerSoldEmailRequestDTO {

    private String playerEmail;

    private String playerName;

    private String franchiseName;

    private BigDecimal soldPrice;

    private String tournamentName;
}
