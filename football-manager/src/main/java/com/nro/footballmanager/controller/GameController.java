package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.service.GameService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController{
    @Autowired
    private GameService gameService;

    @PostMapping("/games")
    public Game saveGame(@Validated @RequestBody Game game){
        return gameService.saveGame(game);
    }

    @GetMapping("/games")
    public List<Game> fetchGamesList(){
        return gameService.fetchGamesList();
    }

    @PutMapping("/games/{id}")
    public Game updateGame(@RequestBody Game game, @PathVariable("id")Long gameId){
        return gameService.updateGame(game, gameId);
    }

    @DeleteMapping("/games/{id}")
    public String deleteGameById(@PathVariable("id")Long gameId){
        try{
            if(!gameService.gameExistance(gameId))
                throw new EntityNotFoundException();
            gameService.deleteGameByID(gameId);
            return "Deleted successfully!";
        } catch (EntityNotFoundException e) {
            return "Deletion failed!";
        }
    }

    @DeleteMapping("/games")
    public String deleteGames(){
        if(gameService.fetchGamesList().isEmpty())
            return "There are no games stored!";
        gameService.deleteGames();
        return "All games deleted!";
    }
}
