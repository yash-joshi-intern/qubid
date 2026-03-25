package com.qubid.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {
    private String name;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal allotedPurse;
}
