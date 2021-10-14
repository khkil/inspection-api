package com.example.backend.common.interceptor;

import com.example.backend.config.secutiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        System.out.println(">>> interceptor.preHandle 호출");
        String token = jwtTokenProvider.resolveToken(request);
        if (token.isEmpty()) {
            throw new IllegalArgumentException("인증정보가 존재하지 않습니다.");
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 인증정보 입니다.");
        }
        String userPk = jwtTokenProvider.getUserPk(token);
        request.setAttribute("userPk", userPk);
        return true;
    }
}