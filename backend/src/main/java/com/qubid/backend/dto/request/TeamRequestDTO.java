package com.qubid.backend.dto.request;

import com.qubid.backend.entities.Contact;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestDTO {

    @NotBlank(message = "Team name can't be blank")
    private String name;

    @NotNull(message = "minPlayers can't be null")
    @Min(value = 1, message = "minPlayers must be at least 1")
    private Integer minPlayers;

    @NotNull(message = "maxPlayers can't be null")
    private Integer maxPlayers;

    private BigDecimal remainingPurse;

    private Contact contactDetails;
}