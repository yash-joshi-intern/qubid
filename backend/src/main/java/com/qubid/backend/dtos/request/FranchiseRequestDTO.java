package com.qubid.backend.dtos.request;

import com.qubid.backend.dtos.response.ContactDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseRequestDTO {
    private String name;
    private String city;
    private String country;
    private ContactDTO contact;
}
