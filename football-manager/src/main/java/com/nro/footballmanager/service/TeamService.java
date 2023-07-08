package com.nro.footballmanager.service;
import com.nro.footballmanager.entity.Team;
import java.util.List;

public interface TeamService {
    Team saveTeam(Team team);

    List<Team> fetchTeamsList();

    Team updateTeam(Team team, Long teamID);

    Boolean teamExistance(Long teamID);

    void deleteTeamByID(Long teamID);

    void deleteTeams();
}
