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
public class BattingStat {
    private Integer runsScored;
    private Integer ballsPlayed;
    //StrikeRate and Average Runs can be calculated based on these above fields.
}
