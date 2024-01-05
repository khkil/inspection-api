package com.example.backend.api.member;

import com.example.backend.util.enumerator.Role;
import com.example.backend.api.group.code.GroupCodeMapper;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    GroupCodeMapper groupCodeMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberRepositorySupport memberRepositorySupport;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("일치하는 정보 가진 회원이 없습니다."));
    }

    public Member loadUserByUserEmail(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("일치하는 이메일을 가진 회원이 없습니다."));
    }

    public boolean checkEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Page<Member> getMemberList(String searchText, Pageable pageable){
        return memberRepositorySupport.getMemberList(searchText, pageable);
    }

    public Member getMemberDetail(int idx){
        Member member = memberRepository.findByIdx(idx).orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        return memberInfo(member);
    }

   /* public Member findIdByInfo(String name, String email){
        return memberMapper.findIdByInfo(name, email);
    }

    public Member findIdByPhone(String phone){
        return memberMapper.findIdByPhone(phone);
    }*/

    @Transactional
    public void signUp(Member member){
        /*String groupCode = member.getGroupCode();
        if(groupCode != null && !groupCode.isEmpty()){
            Group group = groupCodeMapper.getGroupDetailFromCode(groupCode);
            if(group == null) throw new ApiException("유효하지 않은 코드입니다");
            member.setGroupIdx(group.getIdx());
        }*/
        member.setRole(Role.ROLE_MEMBER);
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

   /* public Group getMembersGroup(int memberIdx){
        Group group = memberMapper.getMembersGroup(memberIdx);
        return group;
    }*/

    public void changePassword(int idx, String password){
        Optional<Member> member = memberRepository.findByIdx(idx);
        member.ifPresent(updatedMember -> {
            updatedMember.setPassword(passwordEncoder.encode(password));
            memberRepository.save(updatedMember);
        });
    }

    public void checkDuplicateMember(String id){
        boolean existsMemberById = memberRepository.existsMemberById(id);
        if(existsMemberById) throw new ApiException("이미 사용중인 아이디 입니다.");
    }

    public boolean existsMemberById(String id){
        boolean existsMemberById = memberRepository.existsMemberById(id);
        return existsMemberById;
    }

    public boolean checkPassword(Member user, Member member){

        return passwordEncoder.matches(user.getPassword(), member.getPassword());
    }

    public Member memberInfo(Member member){
        Member memberInfo = member.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .password(member.getPassword())
                .groupIdx(member.getGroupIdx())
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
