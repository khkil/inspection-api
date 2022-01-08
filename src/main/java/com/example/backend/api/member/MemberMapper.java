package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.model.MemberProgress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {

    List<Member> getMemberList();
    Member getMemberDetail(String idx);
    Member loadUserByUserName(String id);
    Member findIdByInfo(String name, String email);
    Member findIdByPhone(String phone);
    void insertMember(Member member);
    void updateMember(String idx, Member member);

    List<MemberProgress> getMemberProgressList(int memberIdx);

}
