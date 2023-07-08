package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.repository.TeamRepository;
import com.nro.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> fetchTeamsList() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public Team updateTeam(Team team, Long teamID) {
        Team old = teamRepository.findById(teamID).get();

        if (Objects.nonNull(team.getName()) && !"".equals(team.getName()))
            old.setName(team.getName());

        if (Objects.nonNull(team.getGoalsScored()))
            old.setGoalsScored(team.getGoalsScored());

        if (Objects.nonNull(team.getGoalsReceived()))
            old.setGoalsReceived(team.getGoalsReceived());

        if (Objects.nonNull(team.getVictories()))
            old.setVictories(team.getVictories());

        if (Objects.nonNull(team.getDefeats()))
            old.setDefeats(team.getDefeats());

        if (Objects.nonNull(team.getDraws()))
            old.setDraws(team.getDraws());

        return teamRepository.save(old);
    }

    @Override
    public Boolean teamExistance(Long teamID) {
        return teamRepository.existsById(teamID);
    }

    @Override
    public void deleteTeamByID(Long teamID) {
        teamRepository.deleteById(teamID);
    }

    @Override
    public void deleteTeams() {
        teamRepository.deleteAll();
    }
}
