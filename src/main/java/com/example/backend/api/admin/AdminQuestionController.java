package com.example.backend.api.admin;

import com.example.backend.api.inspection.question.model.Question;
import com.example.backend.api.inspection.question.QuestionService;
import com.example.backend.api.inspection.question.model.QuestionDto;
import com.example.backend.api.inspection.result.model.Result;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/{questionIdx}")
    public ResponseEntity insertQuestions(@PathVariable int questionIdx){
        QuestionDto.Detail questionDetail = questionService.getQuestionDetail(questionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(questionDetail));
    }

    @DeleteMapping("/{questionIdx}")
    public ResponseEntity deleteQuestion(@PathVariable int questionIdx){
        questionService.deleteQuestion(questionIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PutMapping("/{questionIdx}")
    public ResponseEntity updateQuestion(@PathVariable int questionIdx, @RequestBody Question question){
        questionService.updateQuestion(questionIdx, question);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("")
    public ResponseEntity insertQuestions(@RequestBody List<Question> questions){
        questionService.insertQuestions(questions);
        return ResponseEntity.ok(CommonResponse.successResult());

    }
    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity getQuestionsOfInspection(@PathVariable int inspectionIdx, PageUtil pageUtil){
        PageRequest pageRequest = pageUtil.of(pageUtil);
        List<QuestionDto.Summary> questionsByInspectionIdx = questionService.getQuestionsByInspectionIdx(inspectionIdx, pageRequest);
        return ResponseEntity.ok(CommonResponse.successResult(questionsByInspectionIdx));

    }
}
