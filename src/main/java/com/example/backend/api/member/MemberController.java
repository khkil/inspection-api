package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

}
