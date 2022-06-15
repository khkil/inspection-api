package com.example.backend.api.member;

import com.example.backend.api.auth.model.ResetPasswordVo;
import com.example.backend.api.group.Group;
import com.example.backend.api.group.code.GroupCodeMapper;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.exception.ApiException;
import com.google.protobuf.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    GroupCodeMapper groupCodeMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberMapper.loadUserByUserName(id);

        if(member != null){
            List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
            member.setAuthorities(roles);
        }else{
            throw new UsernameNotFoundException("일치하는 아이디를 가진 회원이 없습니다.");
        }
        return member;
    }

    public Member loadUserByUserEmail(String email) throws UsernameNotFoundException {
        Member member = memberMapper.loadUserByUserEmail(email);

        if(member != null){
            List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
            member.setAuthorities(roles);
        }else{
            throw new UsernameNotFoundException("일치하는 이메일을 가진 회원이 없습니다.");
        }
        return member;
    }

    public List<Member> getMemberList(String searchText){
        return memberMapper.getMemberList(searchText);
    }

    public Member getMemberDetail(int idx){
        Member member = memberMapper.getMemberDetail(idx);

        return memberInfo(member);
    }

    public Member findIdByInfo(String name, String email){
        return memberMapper.findIdByInfo(name, email);
    }

    public Member findIdByPhone(String phone){
        return memberMapper.findIdByPhone(phone);
    }

    public void insertMember(Member member){
        String groupCode = member.getGroupCode();
        if(groupCode != null && !groupCode.isEmpty()){
            Group group = groupCodeMapper.getGroupDetailFromCode(groupCode);
            if(group == null) throw new ApiException("유효하지 않은 코드입니다");
            member.setGroupIdx(group.getIdx());
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberMapper.insertMember(member);
    }

    public void updateMember(int idx, Member member){
        memberMapper.updateMember(idx, member);
    }

    public Group getMembersGroup(int memberIdx){
        Group group = memberMapper.getMembersGroup(memberIdx);
        return group;
    }

    public void changePassword(int idx, String password){
        memberMapper.changePassword(idx, password);
    }

    public void checkDuplicateMember(String id){
        Member member = memberMapper.loadUserByUserName(id);
        if(member != null) throw new ApiException("이미 사용중인 아이디 입니다.");
    }

    public boolean checkPassword(Member user, Member member){
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }

    public Member memberInfo(Member member){
        Member memberInfo = member.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .groupIdx(member.getGroupIdx())
                .password(member.getPassword())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .addressSub(member.getAddressSub())
                .school(member.getSchool())
                .education(member.getEducation())
                .grade(member.getGrade())
                .major(member.getMajor())
                .job(member.getJob())
                .company(member.getCompany())
                .jobDetail(member.getJobDetail())
                .cDate(member.getCDate())
                .build();

        return memberInfo;
    }
}
