package com.example.backend.api.inspection.question;

import com.example.backend.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List> getAllQuestionList (@PathVariable int inspectionIdx, Pageable pageable){
        return new ResponseEntity<>(questionServcice.getQuestionsByInspectionIdx(inspectionIdx, pageable), HttpStatus.OK);
    }

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity<?> getQuestionInfo(@PathVariable int inspectionIdx, PageUtil pageUtil){
        PageRequest pageRequest = pageUtil.of(pageUtil);
        return new ResponseEntity<>(questionServcice.getQuestionListWithAnswers(inspectionIdx, pageRequest), HttpStatus.OK);
    }

    @GetMapping("/inspections/{inspectionIdx}/pages/{page}")
    public ResponseEntity<List> getPageInfo(@PathVariable int inspectionIdx, @PathVariable int page){

        return ResponseEntity.ok(questionServcice.getQuestionsByInspectionIdxAndQuestionPage(inspectionIdx, page));
    }
}
