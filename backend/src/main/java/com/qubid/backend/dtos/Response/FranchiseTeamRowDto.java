package com.qubid.backend.dtos.Response;

import com.qubid.backend.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseTeamRowDto {
    private Long franchiseId;
    private Team team;
}
