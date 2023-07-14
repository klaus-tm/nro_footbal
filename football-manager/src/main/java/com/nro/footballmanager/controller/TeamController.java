package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<Team> saveTeam(@Validated @RequestBody Team team){
        return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> findAllTeams(){
        List<Team> teams = teamService.findAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody Team newTeam, @PathVariable("id")Long id){
        Optional<Team> oldTeam = teamService.getTeamByID(id);
        if (oldTeam.isPresent()){
            return new ResponseEntity<>(teamService.updateTeam(oldTeam.get(), newTeam), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable("id")Long teamId){
        if (!teamService.teamExists(teamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teamService.deleteTeamByID(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
