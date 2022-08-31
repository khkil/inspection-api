package com.example.backend.api.admin;

import com.example.backend.api.inspection.result.model.Result;
import com.example.backend.api.inspection.result.ResultServcice;
import com.example.backend.api.inspection.result.model.ResultDto;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inspections/{inspectionIdx}/results")
public class AdminResultController {

    @Autowired
    ResultServcice resultServcice;

    @GetMapping
    public ResponseEntity getResultList(@PathVariable int inspectionIdx){
        List<ResultDto.Summary> resultList = resultServcice.getInspectionResults(inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(resultList));
    }

    @GetMapping("/{resultIdx}")
    public ResponseEntity getResultDetail(@PathVariable int inspectionIdx, @PathVariable int resultIdx){
        Result result = resultServcice.getResultDetail(inspectionIdx, resultIdx);
        return ResponseEntity.ok(CommonResponse.successResult(result));
    }

    @PostMapping
    public ResponseEntity insertResults(@PathVariable int inspectionIdx, @RequestBody List<Result> resultList){
        resultServcice.insertResults(inspectionIdx, resultList);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @DeleteMapping("/{resultIdx}")
    public ResponseEntity deleteResult(@PathVariable int inspectionIdx, @PathVariable int resultIdx){
        resultServcice.deleteResult(inspectionIdx, resultIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
