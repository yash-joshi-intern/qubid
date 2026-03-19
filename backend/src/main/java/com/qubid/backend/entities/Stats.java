package com.qubid.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    // PLAYERS STATE MATCH OR TOURNAMENT WISE
    private Integer matches;
}
