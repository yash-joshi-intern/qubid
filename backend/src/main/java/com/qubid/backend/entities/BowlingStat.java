package com.qubid.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BowlingStat {

    private Integer wicketsTaken;
    private Integer ballsBowled;
    private Integer runsConceded;
    //Economy and Average Can be Calculated by these fields.
}

