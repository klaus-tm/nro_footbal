package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.repository.GameRepository;
import com.nro.footballmanager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> fetchGamesList() {
        return (List<Game>) gameRepository.findAll();
    }

    @Override
    public Game updateGame(Game game, Long gameID) {
        Game old = gameRepository.findById(gameID).get();

        if(Objects.nonNull(game.getDate()))
            old.setDate(game.getDate());

        if(Objects.nonNull(game.getStartHour()))
            old.setStartHour(game.getStartHour());

        if(Objects.nonNull(game.getResult()))
            old.setResult(game.getResult());

        if(Objects.nonNull(game.getStadium()))
            old.setStadium(game.getStadium());

        if(Objects.nonNull(game.getTeamOne()))
            old.setTeamOne(game.getTeamOne());

        if(Objects.nonNull(game.getTeamTwo()))
            old.setTeamTwo(game.getTeamTwo());

        return gameRepository.save(old);
    }

    @Override
    public Boolean gameExistance(Long gameID) {
        return gameRepository.existsById(gameID);
    }

    @Override
    public void deleteGameByID(Long gameID) {
        gameRepository.deleteById(gameID);
    }

    @Override
    public void deleteGames() {
        gameRepository.deleteAll();
    }
}
