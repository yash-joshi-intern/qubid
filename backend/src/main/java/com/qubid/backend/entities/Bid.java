package com.qubid.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Bid extends BaseEntity {

    private boolean isSold;
    private BigDecimal current_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "auctionPlayer_id"
    )
    private AuctionPlayer auctionPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "auction_id"
    )
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "franchise_id"
    )
    private Franchise franchises_id;



}
