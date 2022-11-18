package com.example.backend.api.inspection.progress;

import com.example.backend.api.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity getMemberProgressDetail(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx){
        return ResponseEntity.ok(progressService.getMemberProgressDetail(member.getIdx(), inspectionIdx));
    }
}
