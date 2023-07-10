package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Result;

import java.util.List;

public interface ResultService {
    Result saveResult(Result result);

    List<Result> fetchResultsList();

    Result updateResult(Result result, Long resultID);

    Boolean resultExistance(Long resultID);

    void deleteResultByID(Long resultID);

    void deleteResults();
}
