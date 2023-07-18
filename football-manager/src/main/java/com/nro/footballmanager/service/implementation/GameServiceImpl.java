package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.repository.GameRepository;
import com.nro.footballmanager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Optional<Game> findGameByID(Long gameID) {
        return gameRepository.findById(gameID);
    }

    @Override
    public Game updateGame(Game oldGame, Game newGame) {
        if(Objects.nonNull(newGame.getDate()))
            oldGame.setDate(newGame.getDate());

        if(Objects.nonNull(newGame.getStartHour()))
            oldGame.setStartHour(newGame.getStartHour());

        if(Objects.nonNull(newGame.getResult()))
            oldGame.setResult(newGame.getResult());

        if(Objects.nonNull(newGame.getStadium()))
            oldGame.setStadium(newGame.getStadium());

        if(Objects.nonNull(newGame.getTeamOne()))
            oldGame.setTeamOne(newGame.getTeamOne());

        if(Objects.nonNull(newGame.getTeamTwo()))
            oldGame.setTeamTwo(newGame.getTeamTwo());

        return gameRepository.save(oldGame);
    }

    @Override
    public Boolean gameExists(Long gameID) {
        return gameRepository.existsById(gameID);
    }

    @Override
    public void deleteGameByID(Long gameID) {
        gameRepository.deleteById(gameID);
    }
}
