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
@Table(name = "franchiese")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team extends  BaseEntity{

    private String name;

    private Integer minPlayers;
    private Integer maxPlayers;

    private BigDecimal remainingPurse;

    @Embedded
    private Contact contact;

    @ManyToMany(mappedBy = "team",fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();

    // which franchise owns this team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

}
