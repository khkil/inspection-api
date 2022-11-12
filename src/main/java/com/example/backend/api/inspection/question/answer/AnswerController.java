package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswerDto;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping("")
    public ResponseEntity insertMemberAnswer(@RequestBody List<MemberAnswerDto> memberAnswer){
        answerService.saveMemberAnswers(memberAnswer);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
