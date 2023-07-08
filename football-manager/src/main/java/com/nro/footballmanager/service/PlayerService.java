package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Player;

import java.util.List;

public interface PlayerService {
    Player savePlayer(Player player);

    List<Player> fetchPlayersList();

    Player updatePlayer(Player player, Long playerID);

    Boolean playerExistance(Long playerID);

    void deletePlayerByID(Long playerID);

    void deletePlayers();
}
