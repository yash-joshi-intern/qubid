package com.qubid.backend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentResponseDTO {

    private Long id;
    private String name;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal allotedPurse;
    private Instant createdAt;
    private Instant updatedAt;

    // Franchises, Teams, Players — excluded intentionally
    // dedicated endpoints handle those
}