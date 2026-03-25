package com.qubid.backend.dtos.response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerUnsoldEventDTO {
    private Long auctionPlayerId;
    private Long playerId;
    private String playerName;
    private AuctionPlayerStatus status;
}
