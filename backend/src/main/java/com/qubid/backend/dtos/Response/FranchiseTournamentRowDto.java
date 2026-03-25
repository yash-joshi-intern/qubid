package com.qubid.backend.dtos.Response;

import com.qubid.backend.entities.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseTournamentRowDto {
    private Long franchiseId;
    private Tournament tournament;
}
