package com.qubid.backend.entities;


import jakarta.persistence.*;
import lombok.*;

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

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String country;

    @Embedded
    private Stats stats;

    @OneToMany(mappedBy = "player")
    private List<BasePrice> basePrice;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "player_team",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> team;

    // many-to-many with skills
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "player_skill",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "player_tournament",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id")
    )
    private List<Tournament> tournamentList = new ArrayList<>();
    // player sport --> single for now
}
