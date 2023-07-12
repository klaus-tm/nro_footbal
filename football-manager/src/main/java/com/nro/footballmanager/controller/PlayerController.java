package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.dto.PlayerDTO;
import com.nro.footballmanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/players")
    public ResponseEntity<Player> savePlayer(@Validated @RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.savePlayer(PlayerDTO.toEntity(playerDTO)), HttpStatus.CREATED);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> findAllPlayers() {
        List<Player> players = playerService.findAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable("id") Long id) {
        Optional<Player> oldPlayer = playerService.getPlayerByID(id);
        if (oldPlayer.isPresent()) {
            return new ResponseEntity<>(PlayerDTO.fromEntity(playerService.updatePlayer(oldPlayer.get(), playerDTO)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/players/{id}")
    public ResponseEntity<HttpStatus> deletePlayerById(@PathVariable("id") Long playerId) {
        if (!playerService.playerExists(playerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        playerService.deletePlayerByID(playerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
