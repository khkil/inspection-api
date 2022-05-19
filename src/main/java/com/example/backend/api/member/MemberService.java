package com.example.backend.api.member;

import com.example.backend.api.auth.model.ResetPasswordVo;
import com.example.backend.api.member.model.Member;
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
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberMapper.loadUserByUserName(id);

        if(member != null){
            List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
            member.setAuthorities(roles);
        }else{
            throw new UsernameNotFoundException("일치하는 아이디를 가진 없습니다.");
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
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberMapper.insertMember(member);
    }

    public void updateMember(int idx, Member member){
        memberMapper.updateMember(idx, member);
    }

    public void changePassword(int idx, String password){
        memberMapper.changePassword(idx, password);
    }

    public Member duplicateMember(String id){
        Member duplicateMember = memberMapper.loadUserByUserName(id);
        return duplicateMember;
    }

    public boolean checkPassword(Member user, Member member){
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }

    public Member memberInfo(Member member){
        Member memberInfo = member.builder()
                .idx(member.getIdx())
                .id(member.getId())
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
