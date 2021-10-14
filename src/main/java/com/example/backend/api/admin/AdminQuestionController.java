package com.example.backend.api.admin;

import com.example.backend.api.answer.Answer;
import com.example.backend.api.question.Question;
import com.example.backend.api.question.QuestionService;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/{questionIdx}")
    public ResponseEntity updateQuestions(@PathVariable int questionIdx){
        Question question = questionService.getQuestionDetail(questionIdx);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{questionIdx}")
    public ResponseEntity deleteQuestion(@PathVariable int questionIdx){
        questionService.deleteQuestion(questionIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PutMapping("/{questionIdx}")
    public ResponseEntity updateQuestion(@PathVariable int questionIdx, @RequestBody Question question){
        questionService.updateQuestion(questionIdx, question);
        List<Answer> answers = question.getAnswers();
        if(answers != null && answers.size() > 0){
            questionService.updateAnswers(question.getAnswers());
        }
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
