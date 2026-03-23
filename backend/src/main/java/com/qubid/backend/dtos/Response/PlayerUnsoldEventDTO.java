package com.qubid.backend.dtos.Response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerUnsoldEventDTO {
    private Long auctionPlayerId;
    private Long playerId;
    private String playerName;
    private AuctionPlayerStatus status;
}
