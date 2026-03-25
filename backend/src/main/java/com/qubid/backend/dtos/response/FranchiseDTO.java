package com.qubid.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDTO {
    private Long id;
    private String name;
    private String city;
    private String country;
    private ContactDTO contact;
}