package com.example.backend.api.inspection.rank;

import com.example.backend.api.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranks")
public class RankController {

    @Autowired
    RankService rankService;

    @GetMapping("/inspections/{inspectionIdx}")
    public ResponseEntity memberInspectionRank(@AuthenticationPrincipal Member member, @PathVariable int inspectionIdx){
        List<Rank> memberInspectionRank = rankService.getMemberInspectionRank(member.getIdx(), inspectionIdx);
        return ResponseEntity.ok(memberInspectionRank);
    }
}
