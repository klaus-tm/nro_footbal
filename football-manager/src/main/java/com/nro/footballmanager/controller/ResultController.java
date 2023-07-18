package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Result;
import com.nro.footballmanager.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping("/results")
    public ResponseEntity<Result> saveResult(@Validated @RequestBody Result result){
        return new ResponseEntity<>(resultService.saveResult(result), HttpStatus.CREATED);
    }

    @GetMapping("/results")
    public ResponseEntity<List<Result>> findAllResults(){
        return new ResponseEntity<>(resultService.findAllResults(), HttpStatus.OK);
    }

    @PutMapping("/results/{id}")
    public ResponseEntity<Result> updateResult(@RequestBody Result newResult, @PathVariable("id")Long resultId){
        Optional<Result>oldResult = resultService.getResultByID(resultId);
        if(oldResult.isPresent())
            return new ResponseEntity<>(resultService.updateResult(oldResult.get(), newResult), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/results/{id}")
    public ResponseEntity<HttpStatus> deleteResultById(@PathVariable("id")Long resultId){
        if(!resultService.resultExistance(resultId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        resultService.deleteResultByID(resultId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
