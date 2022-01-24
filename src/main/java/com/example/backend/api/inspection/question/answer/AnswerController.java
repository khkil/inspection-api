package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswer;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    AnswerServcice answerServcice;

    @PostMapping("")
    public ResponseEntity insertMemberAnswer(@RequestBody MemberAnswer memberAnswer){
        int memberIdx = memberAnswer.getMemberIdx();
        Map<String, Integer> answerMap = memberAnswer.getAnswerMap();
        answerServcice.insertMemberAnswer(memberIdx, answerMap);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
