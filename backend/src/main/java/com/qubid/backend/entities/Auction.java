package com.qubid.backend.entities;

import com.qubid.backend.enums.AuctionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction extends BaseEntity {

    private String venue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status;

    private LocalDate eventDate;

    private String title;

    @OneToMany(mappedBy = "auction")
    private List<AuctionPlayer> auctionPlayerList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

}
