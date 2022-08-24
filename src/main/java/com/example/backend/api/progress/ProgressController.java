package com.example.backend.api.progress;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.progress.model.Progress;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;


    @GetMapping("")
    public ResponseEntity getMyProgressList(@AuthenticationPrincipal Member authenticatedMember){
        int memberIdx = authenticatedMember.getIdx();
        List<Progress> progressList = progressService.getMemberProgressList(memberIdx);
        return ResponseEntity.ok(CommonResponse.successResult(progressList));
    }

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity getMemberProgressDetail(@PathVariable int inspectionIdx, @AuthenticationPrincipal Member authenticatedMember){
        int memberIdx = authenticatedMember.getIdx();
        Progress progressDetail = progressService.getMemberProgressDetail(memberIdx, inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(progressDetail));
    }

    @DeleteMapping("/inspections/{inspectionIdx}")
    public ResponseEntity deleteMemberProgress(@PathVariable int inspectionIdx, @AuthenticationPrincipal Member authenticatedMember){
        int memberIdx = authenticatedMember.getIdx();
        progressService.deleteMemberProgress(memberIdx, inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
