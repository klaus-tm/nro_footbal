package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Result;
import com.nro.footballmanager.repository.ResultRepository;
import com.nro.footballmanager.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Override
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public List<Result> fetchResultsList() {
        return (List<Result>) resultRepository.findAll();
    }

    @Override
    public Result updateResult(Result result, Long resultID) {
        Result old = resultRepository.findById(resultID).get();

        if(Objects.nonNull(result.getGoalsTeamOne()))
            old.setGoalsTeamOne(result.getGoalsTeamOne());

        if(Objects.nonNull(result.getGoalsTeamTwo()))
            old.setGoalsTeamTwo(result.getGoalsTeamTwo());

        return resultRepository.save(old);
    }

    @Override
    public Boolean resultExistance(Long resultID) {
        return resultRepository.existsById(resultID);
    }

    @Override
    public void deleteResultByID(Long resultID) {
        resultRepository.deleteById(resultID);
    }

    @Override
    public void deleteResults() {
        resultRepository.deleteAll();
    }
}
