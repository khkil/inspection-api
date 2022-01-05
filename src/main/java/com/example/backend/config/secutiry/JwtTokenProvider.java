package com.example.backend.config.secutiry;


import com.example.backend.api.auth.redis.RedisService;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    ///private long accessTokenValidMilliseconds = (1000L * 60) * 30; // 30분
    private long accessTokenValidMilliseconds = (1000L) * 5; // 1초
    private long refreshTokenValidMilliseconds = (1000L * 60) * 60 * 24 * 14; // 2주
    private static final String SECRET_KEY = "humanx_sercret_key";
    private static final String REFRESH_TOKEN = "refresh-token";

    private final UserDetailsService userDetailsService;
    @Autowired
    RedisService redisService;

    public String generateAccessToken(String userPk, List<String> roles) {
        return createToken(userPk, roles, accessTokenValidMilliseconds);
    };

    public String generateRefreshToken(String userPk, List<String> roles) {
        return createToken(userPk, roles, refreshTokenValidMilliseconds);
    };

    public String createToken(String userPk, List<String> roles, long expiredTime) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiredTime);
        claims.put("roles", roles);
        claims.put("expire_date", expireDate);
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(expireDate) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public String reissueAccessToken(String refreshToken){

        String userId = getUserPk(refreshToken);
        Jws<Claims> claims = getClaims(refreshToken);
        String redisRefreshToken = redisService.getValues(userId);
        System.out.println(redisRefreshToken);
        System.out.println(refreshToken);
        if(!refreshToken.equals(redisRefreshToken)){
            throw new IllegalArgumentException("Refresh Token 정보가 불일치");
        }
        List<String> roles = (List)claims.getBody().get("roles");
        return generateAccessToken(userId, roles);
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public static String resolveAccessToken(HttpServletRequest request) {

        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public static String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader(REFRESH_TOKEN);
    }

    public Jws<Claims> getClaims(String token){
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token);
        return claims;
    }


    public Date getExpiredDate(String token){

        Jws<Claims> claims = getClaims(token);
        Date expiredDate = claims.getBody().getExpiration();
        return expiredDate;
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN,  refreshToken);
    }



    public void saveRefreshToken2Redis(String userId, String refreshToken){
        redisService.setValues(userId, refreshToken);
    }

    public void removeRefreshToken2Redis(HttpServletRequest request){
        String refreshToken = resolveRefreshToken(request);
        if(!refreshToken.isEmpty()){
            String userPk = getUserPk(refreshToken);
            redisService.deleleteValues(userPk);
        }

    }

    public String getRefreshToken2Redis(String userId){
        return redisService.getValues(userId);
    }
}
