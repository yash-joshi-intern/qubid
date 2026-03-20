package com.qubid.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BowlingStat {

    private Integer wicketsTaken;
    private Integer ballsBowled;
    private Integer runsConceded;
    //Economy and Average Can be Calculated by these fields.
}

