package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.repository.PlayerRepository;
import com.nro.footballmanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> fetchPlayersList() {
        return (List<Player>) playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Player player, Long playerID) {
        Player old = playerRepository.findById(playerID).get();

        if (Objects.nonNull(player.getName()) && !"".equalsIgnoreCase(player.getName()))
            old.setName(player.getName());

        if (Objects.nonNull(player.getGoalsScored()))
            old.setGoalsScored(player.getGoalsScored());

        if (Objects.nonNull(player.getTeam()))
            old.setTeam(player.getTeam());

        if (Objects.nonNull(player.getRole()))
            old.setRole(player.getRole());

        return playerRepository.save(old);
    }

    @Override
    public Boolean playerExistance(Long playerID) {
        return playerRepository.existsById(playerID);
    }

    @Override
    public void deletePlayerByID(Long playerID) {
        playerRepository.deleteById(playerID);
    }

    @Override
    public void deletePlayers() {
        playerRepository.deleteAll();
    }
}
