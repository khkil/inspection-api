package com.example.backend.api.inspection.progress;

import com.example.backend.api.member.model.Member;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity getMemberProgressDetail(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx){
        ProgressDto memberProgress = progressService.getMemberProgressDetail(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgress));
    }

    @PostMapping("/inspections/{inspectionIdx}/check-history")
    public ResponseEntity checkProgressHistory(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx){

        ProgressDto.History memberProgressHistory = progressService.getMemberProgressHistory(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressHistory));
    }

}
