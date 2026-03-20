package com.qubid.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tournament extends BaseEntity {

    private String name;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal allotedPurse;

    @ManyToMany(mappedBy = "tournaments")
    private List<Franchise> franchises = new ArrayList<>();

    @OneToMany(mappedBy = "tournament")
    private List<Team> teams = new ArrayList<>();

    @ManyToMany(mappedBy = "tournaments")
    private List<Player> players = new ArrayList<>();


}
