package com.qubid.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends BaseEntity {

    private String name;
    private Integer rating;
    private Integer yearsOfExp;
    private String expertiseLevel;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();
}