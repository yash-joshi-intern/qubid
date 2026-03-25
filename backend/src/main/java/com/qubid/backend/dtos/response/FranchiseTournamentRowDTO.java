package com.qubid.backend.dtos.response;

import com.qubid.backend.entities.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseTournamentRowDTO {
    private Long franchiseId;
    private Tournament tournament;
}
