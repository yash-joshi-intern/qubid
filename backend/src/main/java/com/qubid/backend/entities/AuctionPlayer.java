package com.qubid.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction_players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionPlayer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToOne
    @JoinColumn(name = "base_price_id")
    private BasePrice basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")   // plain @JoinColumn, NOT @JoinTable
    private Auction auction;

    @OneToMany(mappedBy = "auctionPlayer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();
}