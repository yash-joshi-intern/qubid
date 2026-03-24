package com.qubid.backend.dtos.Response;

import com.qubid.backend.entities.Contact;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String country;
    private Contact contactDetails;
    private Instant createdAt;
    private Instant updatedAt;
}