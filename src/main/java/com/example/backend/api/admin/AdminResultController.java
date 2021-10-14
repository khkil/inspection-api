package com.example.backend.api.admin;

import com.example.backend.api.result.Result;
import com.example.backend.api.result.ResultServcice;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/results")
public class AdminResultController {

    @Autowired
    ResultServcice resultServcice;

    @GetMapping
    public ResponseEntity getResultList(Result result){
        List<Result> resultList = resultServcice.getResultsWithQuestions(result);
        return ResponseEntity.ok(CommonResponse.successResult(resultList));
    }
}
