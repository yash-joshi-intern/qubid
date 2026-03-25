package com.qubid.backend.dtos.request;

import com.qubid.backend.entities.Contact;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRequestDTO {

    @NotBlank(message = "name should not be blank")
    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String country;

    @Valid
    private Contact contactDetails;
}
