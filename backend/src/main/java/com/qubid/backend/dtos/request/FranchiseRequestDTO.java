package com.qubid.backend.dtos.request;

import com.qubid.backend.dtos.response.ContactDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @NotNull(message = "Contact details are required")
    private ContactDTO contact;
}