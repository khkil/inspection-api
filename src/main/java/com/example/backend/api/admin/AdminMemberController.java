package com.example.backend.api.admin;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.MemberService;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public ResponseEntity getMemberList(PageUtil pageUtil, @RequestParam(required = false) String searchText ){
        PageHelper.startPage(pageUtil.getPageNum(), pageUtil.getPageSize());
        PageInfo<Member> memberList = new PageInfo<>(memberService.getMemberList(searchText));
        return ResponseEntity.ok(CommonResponse.successResult(memberList));
    }

    @GetMapping("/{idx}")
    public Member getMemberDetail(@PathVariable String idx){
        return memberService.getMemberDetail(idx);
    }

}
