package com.nro.footballmanager.service;
import com.nro.footballmanager.entity.Team;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAllTeams();
    Optional<Team> getTeamByID(Long teamID);

    Team saveTeam(Team team);

    Team updateTeam(Team team, Team teamID);

    Boolean teamExists(Long teamID);

    void deleteTeamByID(Long teamID);

}
