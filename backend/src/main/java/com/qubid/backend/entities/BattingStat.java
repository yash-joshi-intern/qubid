package com.qubid.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattingStat {
    private Integer runsScored;
    private Integer ballsPlayed;
    //StrikeRate and Average Runs can be calculated based on these above fields.
}
