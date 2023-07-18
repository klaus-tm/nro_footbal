package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class StadiumController {
    @Autowired
    private StadiumService stadiumService;

    @PostMapping("/stadiums")
    public ResponseEntity<Stadium> saveStadium(@Validated @RequestBody Stadium stadium){
        return new ResponseEntity<>(stadiumService.saveStadium(stadium), HttpStatus.CREATED);
    }

    @GetMapping("/stadiums")
    public ResponseEntity<List<Stadium>> findAllStadiums(){
        List<Stadium> stadiums = stadiumService.findAllStadiums();
        return new ResponseEntity<>(stadiums, HttpStatus.OK);
    }

    @GetMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> findStadiumById(@PathVariable("id") Long id){
        if (stadiumService.getStadiumByID(id).isPresent())
            return new ResponseEntity<>(stadiumService.getStadiumByID(id).get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> updateStadium(@RequestBody Stadium newStadium, @PathVariable("id")Long id){
        Optional<Stadium> oldStadium = stadiumService.getStadiumByID(id);
        if (oldStadium.isPresent()){
            return new ResponseEntity<>(stadiumService.updateStadium(oldStadium.get(), newStadium), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/stadiums/{id}")
    public ResponseEntity<HttpStatus> deleteStadiumById(@PathVariable("id")Long stadiumId){
        if (!stadiumService.stadiumExists(stadiumId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stadiumService.deleteStadiumByID(stadiumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
