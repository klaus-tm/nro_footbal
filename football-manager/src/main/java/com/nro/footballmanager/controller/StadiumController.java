package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.service.StadiumService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StadiumController {
    @Autowired
    private StadiumService stadiumService;

    @PostMapping("/stadiums")
    public Stadium saveStadium(@Validated @RequestBody Stadium stadium){
        return stadiumService.saveStadium(stadium);
    }

    @GetMapping("/stadiums")
    public List<Stadium> fetchStadiumsList(){
        return stadiumService.fetchStadiumsList();
    }

    @PutMapping("/stadiums/{id}")
    public Stadium updateStadium(@RequestBody Stadium stadium, @PathVariable("id")Long stadiumId){
        return stadiumService.updateStadium(stadium, stadiumId);
    }

    @DeleteMapping("/stadiums/{id}")
    public String deleteStadiumById(@PathVariable("id")Long stadiumId){
        try{
            if(!stadiumService.stadiumExistance(stadiumId))
                throw new EntityNotFoundException();
            stadiumService.deleteStadiumByID(stadiumId);
            return "Deleted successfully!";
        } catch (EntityNotFoundException e) {
            return "Deletion failed!";
        }
    }

    @DeleteMapping("/stadiums")
    public String deleteStadiums(){
        if(stadiumService.fetchStadiumsList().isEmpty())
            return "There are no stadiums stored!";
        stadiumService.deleteStadiums();
        return "All stadiums deleted!";
    }
}
