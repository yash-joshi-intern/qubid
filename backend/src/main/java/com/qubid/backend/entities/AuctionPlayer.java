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
public class AuctionPlayer extends BaseEntity{

    @OneToOne
    private Player player;

    @OneToOne
    private BasePrice basePrice;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "auction_auctionPlayer",
            joinColumns = @JoinColumn(name = "auctionPlayer_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id")
    )
    private List<Auction> auctionList = new ArrayList<>();

    @OneToMany(mappedBy = "auctionPlayer")
    private List<Bid> bidList = new ArrayList<>();
}
