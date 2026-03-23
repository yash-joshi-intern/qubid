package com.qubid.backend.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattingStatDTO {

    private Integer runsScored;
    private Integer ballsPlayed;
}
