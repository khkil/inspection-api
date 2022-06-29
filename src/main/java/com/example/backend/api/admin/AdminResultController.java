package com.example.backend.api.admin;

import com.example.backend.api.inspection.result.model.Result;
import com.example.backend.api.inspection.result.ResultServcice;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inspections/{inspectionIdx}/results")
public class AdminResultController {

    @Autowired
    ResultServcice resultServcice;

    @GetMapping
    public ResponseEntity getResultList(@PathVariable int inspectionIdx){
        List<Result> resultList = resultServcice.getInspectionResults(inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(resultList));
    }
}
