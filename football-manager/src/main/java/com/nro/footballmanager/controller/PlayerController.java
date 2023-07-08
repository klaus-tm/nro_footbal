package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/players")
    public Player savePlayer(@Validated @RequestBody Player player){
        return playerService.savePlayer(player);
    }

    @GetMapping("/players")
    public List<Player> fetchPlayersList(){
        return playerService.fetchPlayersList();
    }

    @PutMapping("/players/{id}")
    public Player updatePlayer(@RequestBody Player player, @PathVariable("id")Long playerId){
        return playerService.updatePlayer(player, playerId);
    }

    @DeleteMapping("/players/{id}")
    public String deletePlayerById(@PathVariable("id")Long playerId){
        try{
            if(!playerService.playerExistance(playerId))
                throw new EntityNotFoundException();
            playerService.deletePlayerByID(playerId);
            return "Deleted successfully!";
        } catch (EntityNotFoundException e) {
            return "Deletion failed!";
        }
    }

    @DeleteMapping("/players")
    public String deletePlayers(){
        if(playerService.fetchPlayersList().isEmpty())
            return "There are no players stored!";
        playerService.deletePlayers();
        return "All players deleted!";
    }
}
