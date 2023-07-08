package com.nro.footballmanager.controller;

import com.nro.footballmanager.entity.Result;
import com.nro.footballmanager.service.ResultService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping("/results")
    public Result saveResult(@Validated @RequestBody Result result){
        return resultService.saveResult(result);
    }

    @GetMapping("/results")
    public List<Result> fetchResultsList(){
        return resultService.fetchResultsList();
    }

    @PutMapping("/results/{id}")
    public Result updateResult(@RequestBody Result result, @PathVariable("id")Long resultId){
        return resultService.updateResult(result, resultId);
    }

    @DeleteMapping("/results/{id}")
    public String deleteResultById(@PathVariable("id")Long resultId){
        try{
            if(!resultService.resultExistance(resultId))
                throw new EntityNotFoundException();
            resultService.deleteResultByID(resultId);
            return "Deleted successfully!";
        } catch (EntityNotFoundException e) {
            return "Deletion failed!";
        }
    }

    @DeleteMapping("/results")
    public String deleteResults(){
        if(resultService.fetchResultsList().isEmpty())
            return "There are no results stored!";
        resultService.deleteResults();
        return "All results deleted!";
    }
}
