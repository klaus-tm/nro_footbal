package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.dto.PlayerDTO;
import com.nro.footballmanager.repository.PlayerRepository;
import com.nro.footballmanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayerByID(Long playerID) {
        return playerRepository.findById(playerID);
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player, PlayerDTO playerDTO) {
        player = PlayerDTO.toEntityUpdate(playerDTO, player);
        return savePlayer(player);
    }

    @Override
    public Boolean playerExists(Long playerID) {
        return playerRepository.existsById(playerID);
    }

    @Override
    public void deletePlayerByID(Long playerID) {
        playerRepository.deleteById(playerID);
    }
}
