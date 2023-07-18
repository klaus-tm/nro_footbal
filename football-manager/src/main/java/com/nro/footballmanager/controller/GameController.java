package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController{
    @Autowired
    private GameService gameService;

    @PostMapping("/games")
    public ResponseEntity<Game> saveGame(@Validated @RequestBody Game game){
        return new ResponseEntity<>(gameService.saveGame(game), HttpStatus.CREATED);
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> findAllGames(){
        return new ResponseEntity<>(gameService.findAllGames(), HttpStatus.OK);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> findGameByID(@PathVariable("id") Long gameID){
        if(gameService.findGameByID(gameID).isPresent())
            return new ResponseEntity<>(gameService.findGameByID(gameID).get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@RequestBody Game newGame, @PathVariable("id")Long gameId){
        Optional<Game> oldGame = gameService.findGameByID(gameId);
        if (oldGame.isPresent())
            return new ResponseEntity<>(gameService.updateGame(oldGame.get(), newGame), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<HttpStatus> deleteGameById(@PathVariable("id")Long gameId){
        if (!gameService.gameExists(gameId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        gameService.deleteGameByID(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
