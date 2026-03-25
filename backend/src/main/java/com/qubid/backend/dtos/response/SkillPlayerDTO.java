package com.qubid.backend.dtos.response;

import com.qubid.backend.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillPlayerDTO {
    private Long skillId;
    private List<Player> players;
}