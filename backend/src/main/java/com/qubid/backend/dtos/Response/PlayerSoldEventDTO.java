package com.qubid.backend.dtos.Response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSoldEventDTO {
    private Long auctionPlayerId;
    private Long playerId;
    private String playerName;
    private Long soldToFranchiseId;
    private String soldToFranchiseName;
    private BigDecimal finalPrice;
    private BigDecimal franchiseRemainingPurse;
    private AuctionPlayerStatus status;
}
