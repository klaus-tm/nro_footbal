package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> findAllPlayers();

    Optional<Player> getPlayerByID(Long playerID);

    Player savePlayer(Player player);

    Player updatePlayer(Player player, PlayerDTO playerDTO);

    Boolean playerExists(Long playerID);

    void deletePlayerByID(Long playerID);
}
