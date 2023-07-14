package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.repository.TeamRepository;
import com.nro.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> getTeamByID(Long teamID) {
        return teamRepository.findById(teamID);
    }

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team oldTeam, Team newTeam) {
        if (Objects.nonNull(newTeam.getName()) && !"".equals(newTeam.getName()))
            oldTeam.setName(newTeam.getName());

        if (Objects.nonNull(newTeam.getGoalsScored()))
            oldTeam.setGoalsScored(newTeam.getGoalsScored());

        if (Objects.nonNull(newTeam.getGoalsReceived()))
            oldTeam.setGoalsReceived(newTeam.getGoalsReceived());

        if (Objects.nonNull(newTeam.getVictories()))
            oldTeam.setVictories(newTeam.getVictories());

        if (Objects.nonNull(newTeam.getDefeats()))
            oldTeam.setDefeats(newTeam.getDefeats());

        if (Objects.nonNull(newTeam.getDraws()))
            oldTeam.setDraws(newTeam.getDraws());

        return saveTeam(oldTeam);
    }

    @Override
    public Boolean teamExists(Long teamID) {
        return teamRepository.existsById(teamID);
    }

    @Override
    public void deleteTeamByID(Long teamID) {
        teamRepository.deleteById(teamID);
    }

}
