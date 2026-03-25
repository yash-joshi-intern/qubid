package com.qubid.backend.dtos.response;

import com.qubid.backend.entities.Team;
import com.qubid.backend.entities.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponseDTO {
    private Long id;
    private String name;
    private String city;
    private String country;
    private ContactDTO contact;

    // TODO: Replace Team entity exposure with TeamResponseDto once Team module owner delivers DTO.
    private List<Team> teams;

    // TODO: Replace Tournament entity exposure with TournamentResponseDto once Tournament module owner delivers DTO.
    private List<Tournament> tournaments;
}
