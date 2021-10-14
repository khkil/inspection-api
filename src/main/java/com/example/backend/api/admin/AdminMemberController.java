package com.example.backend.api.admin;

import com.example.backend.api.member.Member;
import com.example.backend.api.member.MemberService;
import com.example.backend.api.user.User;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {


    @Autowired

    MemberService memberService;

    @GetMapping
    public ResponseEntity getMemberList(PageUtil pageUtil){
        PageHelper.startPage(pageUtil.getPageNum(), pageUtil.getPageSize());
        PageInfo<Member> memberList = new PageInfo<>(memberService.getMemberList());
        return ResponseEntity.ok(CommonResponse.successResult(memberList));
    }

    @GetMapping("/{idx}")
    public Member getMemberDetail(@PathVariable String idx){
        return memberService.getMemberDetail(idx);
    }

}
