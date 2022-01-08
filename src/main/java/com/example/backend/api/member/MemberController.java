package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.model.MemberProgress;
import com.example.backend.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    MemberService memberService;
    @GetMapping("")
    public String test(){
        return "member";
    }

    @PutMapping("/{idx}")
    public ResponseEntity updateMember(@PathVariable String idx, @RequestBody Member member){
        memberService.updateMember(idx, member);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @GetMapping("/{idx}/progress")
    public ResponseEntity getMemberProgressList(@PathVariable int idx){
        List<MemberProgress> memberProgressList = memberService.getMemberProgressList(idx);
        return ResponseEntity.ok(CommonResponse.successResult(memberProgressList));
    }
}
