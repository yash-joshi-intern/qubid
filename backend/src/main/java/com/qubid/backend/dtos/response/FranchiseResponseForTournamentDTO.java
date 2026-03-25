package com.qubid.backend.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponseForTournamentDTO {

    private Long id;
    private String name;
    private String city;
    private String country;

}
