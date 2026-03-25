package com.qubid.backend.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentRequestDTO {

    @NotBlank(message = "Tournament name can't be blank")
    private String name;

    private String location;

    @NotNull(message = "Start date can't be null")
    private LocalDate startDate;

    @NotNull(message = "End date can't be null")
    private LocalDate endDate;

    private BigDecimal allotedPurse;
}