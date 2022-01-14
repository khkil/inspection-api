package com.example.backend.api.auth;

import com.example.backend.api.auth.model.Role;
import com.example.backend.api.member.model.Member;
import com.example.backend.config.secutiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public void loginSuccess(Member member, HttpServletResponse response){

        List<String> memberRoles = Arrays.asList(member.getRole());
        String accessToken =  jwtTokenProvider.generateAccessToken(member.getId(), memberRoles);
        String refreshToken =  jwtTokenProvider.generateRefreshToken(member.getId(), memberRoles);

        jwtTokenProvider.setCookieAccessToken(accessToken, response);
        jwtTokenProvider.setCookieRefreshToken(refreshToken, response);
        jwtTokenProvider.saveRefreshToken2Redis(member.getId(), refreshToken);
    }

    public void logoutSuccess(HttpServletRequest request, HttpServletResponse response){

        jwtTokenProvider.removeRefreshToken2Redis(request);
        jwtTokenProvider.deleteCookie(response);
    }
}
