package com.qubid.backend.dtos.Response;

import com.qubid.backend.entities.Contact;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDTO {

    private Long id;
    private String name;
    private Integer minPlayers;
    private Integer maxPlayers;
    private BigDecimal remainingPurse;
    private Contact contactDetails;
    private Instant createdAt;
    private Instant updatedAt;

    // Franchise & Tournament intentionally excluded
    // —they will have separate endpoints
}