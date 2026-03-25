package com.qubid.backend.dtos.response;

import com.qubid.backend.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillPlayerRowDTO {
    private Long SkillId;
    private Player player;
}
