package com.example.backend.api.auth;

import com.example.backend.api.auth.model.ResetPasswordVo;
import com.example.backend.util.enumerator.Role;
import com.example.backend.api.member.MemberService;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.exception.ApiException;
import com.example.backend.config.secutiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member login(Member user, HttpServletResponse response) throws Exception {
        String userName = user.getUsername();
        Member member = (Member) memberService.loadUserByUsername(userName);
        if(member == null){
            throw new UsernameNotFoundException("일치하는 아이디를 가진 회원이 없습니다");
        }
        if(passwordEncoder.matches(user.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
        loginSuccess(member, response);
        return memberService.memberInfo(member);
    }

    public void loginSuccess(Member member, HttpServletResponse response){

        Role role = member.getRole();
        String accessToken =  jwtTokenProvider.generateAccessToken(member.getId(), role);
        String refreshToken =  jwtTokenProvider.generateRefreshToken(member.getId(), role);

        jwtTokenProvider.setCookieAccessToken(accessToken, response);
        jwtTokenProvider.setCookieRefreshToken(refreshToken, response);
        jwtTokenProvider.saveRefreshToken2Redis(member.getId(), refreshToken);
    }

    public void logoutSuccess(HttpServletRequest request, HttpServletResponse response){

        jwtTokenProvider.removeRefreshToken2Redis(request);
        jwtTokenProvider.deleteCookie(response);
    }

    public void ResetPassword(int idx, ResetPasswordVo resetPasswordVo){
        //Member member = memberService.getMemberDetail(idx);
        Member member = new Member();
        String memberPassword = member.getPassword();
        String inputPassword  = resetPasswordVo.getPassword();

        if(!passwordEncoder.matches(inputPassword, memberPassword)){
            throw new ApiException("현재 비밀번호가 일치하지 않습니다.");
        }
        //memberService.changePassword(idx, passwordEncoder.encode(resetPasswordVo.getNewPassword()));

    }
}
