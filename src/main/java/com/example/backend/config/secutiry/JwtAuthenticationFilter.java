package com.example.backend.config.secutiry;

import com.example.backend.api.auth.redis.RedisService;
import com.example.backend.common.exception.UserAuthorityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, RedisService redisService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        String refreshToken = null;
        try{
            accessToken = jwtTokenProvider.resolveAccessToken(request);
            refreshToken = jwtTokenProvider.resolveRefreshToken(request);
            if(accessToken != null){
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                String userPk = jwtTokenProvider.getUserPk(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (ExpiredJwtException e){
            logger.error("토큰 만료");
            e.printStackTrace();
            if(refreshToken != null && jwtTokenProvider.validateToken(refreshToken)){
                String userPk = jwtTokenProvider.getUserPk(refreshToken);
                String redisRefreshToken = redisService.getValues(userPk);
                if(redisRefreshToken != null && redisRefreshToken.equals(refreshToken)){
                    Jws<Claims> claims = jwtTokenProvider.getClaims(refreshToken);
                    List<String> userRoles = (List<String>) claims.getBody().get("roles");
                    String newAccessToken = jwtTokenProvider.generateAccessToken(userPk, userRoles);
                    Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                    jwtTokenProvider.setCookieAccessToken(newAccessToken, response);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
