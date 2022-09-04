package com.example.backend.api.admin;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.MemberService;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public ResponseEntity getMemberList(@RequestParam(required = false) String searchText, PageUtil pageUtil){
        PageRequest pageRequest = PageRequest.of(pageUtil.getOffset(), pageUtil.getLimit());
        Page<Member> memberList = memberService.getMemberList(searchText, pageRequest);
        return ResponseEntity.ok(CommonResponse.successResult(memberList));
    }

    @GetMapping("/{idx}")
    public ResponseEntity getMemberDetail(@PathVariable int idx){
        Member memberDetail = memberService.getMemberDetail(idx);
        return ResponseEntity.ok(CommonResponse.successResult(memberDetail));
    }

    @PutMapping("/{idx}")
    public ResponseEntity updateMember(@PathVariable int idx, @RequestBody Member member){
        memberService.updateMember(idx, member);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

}
