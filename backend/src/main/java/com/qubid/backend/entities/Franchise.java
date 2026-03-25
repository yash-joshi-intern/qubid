package com.qubid.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "franchises")   // was "franchiese"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Franchise extends BaseEntity {

    private String name;
    private String city;
    private String country;

    @Embedded
    private Contact contact;

    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "franchise_tournament",
            joinColumns = @JoinColumn(name = "franchise_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> tournaments = new ArrayList<>();
}