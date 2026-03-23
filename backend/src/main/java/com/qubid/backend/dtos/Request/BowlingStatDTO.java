package com.qubid.backend.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BowlingStatDTO {

    private Integer wicketsTaken;
    private Integer ballsBowled;
    private Integer runsConceded;
}
