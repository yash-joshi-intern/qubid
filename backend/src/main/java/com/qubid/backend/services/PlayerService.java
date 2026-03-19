package com.qubid.backend.services;

import com.qubid.backend.entities.Player;

import java.util.List;

public interface PlayerService {
     List<Player> getAll();
     Player add(Player player);
     Player getById(Long id);
     List<Player> getByName(String name);
}
