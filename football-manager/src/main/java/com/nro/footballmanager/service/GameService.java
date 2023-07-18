package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Game saveGame(Game game);

    List<Game> findAllGames();

    Optional<Game> findGameByID(Long gameID);

    Game updateGame(Game oldGame, Game newGame);

    Boolean gameExists(Long gameID);

    void deleteGameByID(Long gameID);
}
