package com.qubid.backend.dtos.Response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerLiveEventDTO {
    private Long auctionPlayerId;
    private Long playerId;
    private String firstName;
    private String lastName;
    private String country;
    private BigInteger basePrice;
    private AuctionPlayerStatus status;
}
