package com.qubid.backend.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattingStatDTO {

    @NotNull(message = "Runs scored must not be null")
    @Min(value = 0, message = "Runs scored cannot be negative")
    private Integer runsScored;

    @NotNull(message = "Balls played must not be null")
    @Min(value = 0, message = "Balls played cannot be negative")
    private Integer ballsPlayed;
}
