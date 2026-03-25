package com.qubid.backend.dtos.Request;

import com.qubid.backend.dtos.Response.ContactDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseRequestDto {
    private String name;
    private String city;
    private String country;
    private ContactDto contact;
}
