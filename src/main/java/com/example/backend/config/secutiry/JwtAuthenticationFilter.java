package com.example.backend.config.secutiry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if(accessToken != null){
            if(jwtTokenProvider.validateToken(accessToken)){
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else if(!jwtTokenProvider.validateToken(accessToken) && refreshToken  != null){
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);

                if(validateRefreshToken){
                    String userId = jwtTokenProvider.getUserPk(refreshToken);
                    if(jwtTokenProvider.getRefreshToken2Redis(userId) != null){
                        Jws<Claims> claims = jwtTokenProvider.getClaims(refreshToken);
                        List<String> userRoles = (List<String>) claims.getBody().get("roles");
                        String newAccessToken = jwtTokenProvider.generateAccessToken(userId, userRoles);
                        jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                        Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
