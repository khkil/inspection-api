package com.example.backend.api.inspection.result;

import com.example.backend.api.inspection.result.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ResultServcice {

    @Autowired
    ResultMapper resultMapper;

    @Autowired
    ResultRepository resultRepository;

    public List<Result> getInspectionResults(int inspectionIdx){
        List<Result> inspectionResults = resultRepository.findAllByInspectionIdx(inspectionIdx);
        return inspectionResults;
    }

    public List<Result> getResults(Map<String, Object> params){
        return resultMapper.getResults(params);
    }

    public Result getResultDetail(int inspectionIdx, int resultIdx){
        return resultMapper.getResultDetail(inspectionIdx, resultIdx);
    }

    public void insertResults(int inspectionIdx, List<Result> resultList) {
        if(resultList.size() > 0){
            resultMapper.insertResults(inspectionIdx, resultList);
        }
    }

    public void deleteResult(int inspectionIdx, int resultIdx){
        resultMapper.deleteResult(inspectionIdx, resultIdx);
    }


}
