package com.qubid.backend.dtos.response;

import com.qubid.backend.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseTeamRowDTO {
    private Long franchiseId;
    private Team team;
}
