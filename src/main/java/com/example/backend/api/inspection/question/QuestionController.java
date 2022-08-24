package com.example.backend.api.inspection.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/questions")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionServcice;

    @GetMapping
    public ResponseEntity<List> getAllQuestionList (@PathVariable int inspectionIdx){
        return new ResponseEntity<>(questionServcice.getQuestionsByInspectionIdx(inspectionIdx), HttpStatus.OK);
    }

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity<?> getQuestionInfo(@PathVariable int inspectionIdx){

        return new ResponseEntity<>(questionServcice.getQuestionListWithAnswers(inspectionIdx), HttpStatus.OK);
    }

    @GetMapping("/inspections/{inspection_idx}/pages/{page}")
    public ResponseEntity<List> getPageInfo(@PathVariable int inspection_idx, @PathVariable int page){

        return ResponseEntity.ok(questionServcice.getQuestionsByInspectionIdxAndQuestionPage(inspection_idx, page));
    }
}
