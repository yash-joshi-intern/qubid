package com.qubid.backend.dtos.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BowlingStatDTO {

    @NotNull(message = "Wickets taken must not be null")
    @Min(value = 0, message = "Wickets taken cannot be negative")
    private Integer wicketsTaken;

    @NotNull(message = "Balls bowled must not be null")
    @Min(value = 0, message = "Balls bowled cannot be negative")
    private Integer ballsBowled;

    @NotNull(message = "Runs conceded must not be null")
    @Min(value = 0, message = "Runs conceded cannot be negative")
    private Integer runsConceded;
}
