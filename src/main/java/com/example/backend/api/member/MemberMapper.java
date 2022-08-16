package com.example.backend.api.member;

import com.example.backend.api.auth.model.ResetPasswordVo;
import com.example.backend.api.group.Group;
import com.example.backend.api.member.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {

    List<Member> getMemberList(String searchText);
    Member getMemberDetail(int idx);
    Member loadUserByUserName(String id);
    Member loadUserByUserEmail(String email);
    Member findIdByInfo(String name, String email);
    Member findIdByPhone(String phone);
    Group getMembersGroup(int memberIdx);
    void updateMember(int idx, Member member);
    void changePassword(int idx, String password);
}
