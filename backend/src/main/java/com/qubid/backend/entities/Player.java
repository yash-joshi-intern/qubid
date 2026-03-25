package com.qubid.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends BaseEntity {

    @NotNull(message = "firstName can't be null")
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String country;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Stats> stats;

    @Embedded
    private Contact contactDetails;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasePrice> basePrices = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_team",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_skill",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_tournament",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuctionPlayer> auctionPlayers = new ArrayList<>();
}