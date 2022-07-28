package com.example.backend.api.inspection.result;

import com.example.backend.api.inspection.result.model.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResultMapper {

    List<Result> getInspectionResults(int inspectionIdx);
    List<Result> getResults(Map<String, Object> params);
    List<Result> getResultsWithQuestions(Result result);
    Result getResultDetail(int inspectionIdx, int resultIdx);
    void insertResults(int inspectionIdx, List<Result> resultList);
    void deleteResult(int inspectionIdx, int resultIdx);


}
