package com.example.backend.api.inspection.question.answer;

import com.example.backend.api.inspection.question.answer.model.MemberAnswerDto;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    MemberAnswerService answerService;

    @PostMapping("")
    public ResponseEntity insertMemberAnswers(@AuthenticationPrincipal Member member, @RequestBody List<MemberAnswerDto> memberAnswers){
        answerService.saveMemberAnswers(member.getIdx(), memberAnswers);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @DeleteMapping("/inspections/{inspectionIdx}")
    public ResponseEntity deleteMemberAnswers(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx) {
        answerService.deleteMemberAnswers(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
