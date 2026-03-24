package com.qubid.backend.entities;

import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.enums.HighestLevel;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stats extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CricketFormat cricketFormat;

    private Integer matchesPlayed;

    @Embedded
    private BattingStat battingStat;

    @Embedded
    private BowlingStat bowlingStat;

    @Enumerated(EnumType.STRING)
    private HighestLevel highestLevel;
}
