package com.nro.footballmanager.entity.dto;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.enums.RoleEnum;
import lombok.Data;

@Data
public class PlayerDTO {
    private String name;
    private Integer goalsScored;
    private RoleEnum role;
    private Long teamID;

    public static PlayerDTO fromEntity(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setName(player.getName());
        playerDTO.setGoalsScored(player.getGoalsScored());
        playerDTO.setRole(player.getRole());
        playerDTO.setTeamID(player.getTeam().getId());

        return playerDTO;
    }
    public static Player toEntity(PlayerDTO playerDTO) {
        Player player = new Player();

        player.setName(playerDTO.getName());
        player.setRole(playerDTO.getRole());
        player.setGoalsScored(playerDTO.getGoalsScored());
        player.getTeam().setId(playerDTO.getTeamID());

        return player;
    }

    public static Player toEntityUpdate(PlayerDTO playerDTO, Player player) {

        player.setName(playerDTO.getName());
        player.setRole(playerDTO.getRole());
        player.setGoalsScored(playerDTO.getGoalsScored());
        player.getTeam().setId(playerDTO.getTeamID());

        return player;
    }
}
