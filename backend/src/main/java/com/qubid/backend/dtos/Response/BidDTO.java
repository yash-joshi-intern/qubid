package com.qubid.backend.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long bidId;
    private Long auctionPlayerId;
    private String playerName;
    private Long franchiseId;
    private String franchiseName;
    private BigDecimal bidAmount;
    private boolean isSold;
}
