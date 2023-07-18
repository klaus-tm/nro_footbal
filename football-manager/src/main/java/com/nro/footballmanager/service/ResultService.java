package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    List<Result> findAllResults();

    Optional<Result> getResultByID(Long resultID);

    Result saveResult(Result result);

    Result updateResult(Result oldResult, Result newResult);

    Boolean resultExistance(Long resultID);

    void deleteResultByID(Long resultID);

}
