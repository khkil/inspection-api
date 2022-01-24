package com.example.backend.api.member;

import com.example.backend.api.inspection.Inspection;
import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.model.MemberProgress;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;
    @GetMapping("")
    public String test(){
        return "member";
    }

    @PutMapping("")
    public ResponseEntity updateMember(@RequestBody Member member, @AuthenticationPrincipal Member authenticatedMember){
        int memberIdx = authenticatedMember.getIdx();
        memberService.updateMember(memberIdx, member);
        return ResponseEntity.ok(CommonResponse.successResult(memberService.memberInfo(member)));
    }

    @GetMapping("/{idx}/progress")
    public ResponseEntity getMemberProgressList(@PathVariable int idx, @RequestBody(required = false) Inspection inspection){
        List<MemberProgress> memberProgressList = memberService.getMemberProgressList(idx, inspection);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressList));
    }

    @GetMapping("/{idx}/progress/inspections/{inspectionIdx}")
    public ResponseEntity getMemberProgressDetail(@PathVariable int idx, @PathVariable int inspectionIdx){
        MemberProgress memberProgressDetail = memberService.getMemberProgressDetail(idx, inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressDetail));
    }

    @DeleteMapping("/{idx}/progress/inspections/{inspectionIdx}")
    public ResponseEntity deleteMemberProgress(@PathVariable int idx, @PathVariable int inspectionIdx, Principal principal){
        memberService.deleteMemberProgress(idx, inspectionIdx);
        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
