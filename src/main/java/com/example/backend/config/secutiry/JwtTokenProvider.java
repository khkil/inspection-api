package com.example.backend.config.secutiry;


import com.example.backend.api.auth.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private long accessTokenValidMilliseconds = (1000L * 60) * 30; // 30분
    //private long accessTokenValidMilliseconds = (1000L) * 5; // 1초
    private long refreshTokenValidMilliseconds = (1000L * 60) * 60 * 24 * 14; // 2주
    private static final String SECRET_KEY = "humanx_sercret_key";
    public static final String AUTHORIZATION = "Authorization";
    private static Clock clock = DefaultClock.INSTANCE;

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
        return request.getHeader(AUTHORIZATION);
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

    public static boolean validateToken(HttpServletRequest request){
        String jwtToken = resolveAccessToken(request);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveRefreshToken2Redis(String userId, String refreshToken){
        redisService.setValues(userId, refreshToken);
    }

    public void removeRefreshToken2Redis(HttpServletRequest request){
        String accessToken = resolveAccessToken(request);
        String userPk = getUserPk(accessToken);
        redisService.deleleteValues(userPk);
    }

    public String getRefreshToken2Redis(String userId){
        return redisService.getValues(userId);
    }
}
