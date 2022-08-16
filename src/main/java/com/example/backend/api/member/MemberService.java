package com.example.backend.api.member;

import com.example.backend.api.group.Group;
import com.example.backend.api.group.code.GroupCodeMapper;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    GroupCodeMapper groupCodeMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("일치하는 정보 가진 회원이 없습니다."));
    }

    public Member loadUserByUserEmail(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("일치하는 이메일을 가진 회원이 없습니다."));

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

    public void signUp(Member member){
        /*String groupCode = member.getGroupCode();
        if(groupCode != null && !groupCode.isEmpty()){
            Group group = groupCodeMapper.getGroupDetailFromCode(groupCode);
            if(group == null) throw new ApiException("유효하지 않은 코드입니다");
            member.setGroupIdx(group.getIdx());
        }*/
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public void updateMember(int idx, Member params){
        Optional<Member> member = memberRepository.findByIdx(idx);
        member.ifPresent(updatedMember -> {
            updatedMember.setName(params.getName());
            updatedMember.setEmail(params.getEmail());
            updatedMember.setPhone(params.getPhone());
            updatedMember.setAddress(params.getAddress());
            updatedMember.setAddressSub(params.getAddressSub());
            updatedMember.setSchool(params.getSchool());
            updatedMember.setEducation(params.getEducation());
            updatedMember.setGrade(params.getGrade());
            updatedMember.setMajor(params.getMajor());
            updatedMember.setJob(params.getJob());
            updatedMember.setCompany(params.getCompany());
            updatedMember.setJobDetail(params.getJobDetail());
            memberRepository.save(updatedMember);
        });

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
