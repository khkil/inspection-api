package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.model.MemberProgress;
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
        }
        return member;
    }

    public List<Member> getMemberList(){
        return memberMapper.getMemberList();
    }

    public Member getMemberDetail(String idx){
        return memberMapper.getMemberDetail(idx);
    }

    public Member findIdByInfo(String name, String email){
        return memberMapper.findIdByInfo(name, email);
    }

    public Member findIdByPhone(String phone){
        return memberMapper.findIdByPhone(phone);
    }

    public void insertMember(Member member){
        memberMapper.insertMember(
                Member.builder()
                    .id(member.getId())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .role(member.getRole())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .build()
        );
    }

    public void updateMember(String idx, Member member){
        memberMapper.updateMember(idx, member);
    }

    public Member duplicateMember(Member member){
        Member duplicateMember = memberMapper.loadUserByUserName(member.getId());
        return duplicateMember;
    }

    public boolean checkPassword(Member user, Member member){
        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }

    public List<MemberProgress> getMemberProgressList(int memberIdx){
        return memberMapper.getMemberProgressList(memberIdx);
    }
}
