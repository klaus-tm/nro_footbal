package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Result;
import com.nro.footballmanager.repository.ResultRepository;
import com.nro.footballmanager.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;

    @Override
    public List<Result> findAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> getResultByID(Long resultID) {
        return resultRepository.findById(resultID);
    }

    @Override
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public Result updateResult(Result oldResult, Result newResult) {
        if(Objects.nonNull(newResult.getGoalsTeamOne()))
            oldResult.setGoalsTeamOne(newResult.getGoalsTeamOne());

        if(Objects.nonNull(newResult.getGoalsTeamTwo()))
            oldResult.setGoalsTeamTwo(newResult.getGoalsTeamTwo());

        if(Objects.nonNull(newResult.getGame()))
            oldResult.setGame(newResult.getGame());

        return resultRepository.save(oldResult);
    }

    @Override
    public Boolean resultExistance(Long resultID) {
        return resultRepository.existsById(resultID);
    }

    @Override
    public void deleteResultByID(Long resultID) {
        resultRepository.deleteById(resultID);
    }

}
