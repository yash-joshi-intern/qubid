package com.qubid.backend.entities;

import jakarta.persistence.*;
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
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer rating;        // numeric rating for skill
    private Integer yearsOfExp;
    private String expertiseLevel;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();


}
