package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    public Team saveTeam(@Validated @RequestBody Team team){
        return teamService.saveTeam(team);
    }

    @GetMapping("/teams")
    public List<Team> fetchTeamsList(){
        return teamService.findAllTeams();
    }

    @PutMapping("/teams/{id}")
    public Team updateTeam(@RequestBody Team team, @PathVariable("id")Long teamId){
        return teamService.updateTeam(team, teamId);
    }

    @DeleteMapping("/teams/{id}")
    public String deleteTeamById(@PathVariable("id")Long teamId){
        try{
            if(!teamService.teamExists(teamId))
                throw new EntityNotFoundException();
            teamService.deleteTeamByID(teamId);
            return "Deleted successfully!";
        } catch (EntityNotFoundException e) {
            return "Deletion failed!";
        }
    }
//
//    @DeleteMapping("/teams")
//    public String deleteTeams(){
//        if (teamService.findAllTeams().isEmpty())
//            return "There are no teams stored!";
//        teamService.deleteTeams();
//        return "All teams deleted!";
//    }
}
