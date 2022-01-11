package com.example.backend.config.secutiry;


import com.example.backend.api.auth.redis.RedisService;

import com.example.backend.util.CookieUtil;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private long accessTokenValidMilliseconds = (1000L * 60) * 30; // 30분
    //private long accessTokenValidMilliseconds = (1000L) * 3; // 5초
    private long refreshTokenValidMilliseconds = (1000L * 60) * 60 * 24; // 24시간
    private static final String SECRET_KEY = "humanx_sercret_key";
    private static final String REFRESH_TOKEN = "RefreshToken";

    private final UserDetailsService userDetailsService;

    @Autowired
    CookieUtil cookieUtil;
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
    public String resolveAccessToken(HttpServletRequest request) {

        Cookie cookie = cookieUtil.getCookie(request, HttpHeaders.AUTHORIZATION);
        return cookie.getValue();
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie cookie = cookieUtil.getCookie(request, REFRESH_TOKEN);
        return cookie.getValue();
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
        } catch (ExpiredJwtException e){
            log.error("토큰 만료");
            return false;
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void setCookieAccessToken(String accessToken, HttpServletResponse response) {
        Cookie cookie = cookieUtil.createCookie(HttpHeaders.AUTHORIZATION, accessToken, (int)accessTokenValidMilliseconds);
        response.addCookie(cookie);
    }

    public void setCookieRefreshToken(String refreshToken, HttpServletResponse response) {
        Cookie cookie = cookieUtil.createCookie(REFRESH_TOKEN, refreshToken, (int)refreshTokenValidMilliseconds);
        response.addCookie(cookie);
    }


    public void deleteCookie(HttpServletResponse response){
        Cookie accessTokenCookie = cookieUtil.createCookie(HttpHeaders.AUTHORIZATION, null, 0);
        Cookie refreshTokenCookie = cookieUtil.createCookie(REFRESH_TOKEN, null, 0);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public void saveRefreshToken2Redis(String userId, String refreshToken){
        redisService.setValuesExpire(userId, refreshToken, refreshTokenValidMilliseconds);
    }

    public void removeRefreshToken2Redis(HttpServletRequest request){
        String refreshToken = resolveRefreshToken(request);
        if(!refreshToken.isEmpty()){
            String userPk = getUserPk(refreshToken);
            redisService.deleteValues(userPk);
        }
    }

    public String getRefreshToken2Redis(String userId){
        return redisService.getValues(userId);
    }
}
