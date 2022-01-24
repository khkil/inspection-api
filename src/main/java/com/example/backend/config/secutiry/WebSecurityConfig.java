package com.example.backend.config.secutiry;

import com.example.backend.api.auth.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RedisService redisService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable() // csrf 보안 토큰 disable처리.
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증 이므로 세션 역시 사용x
            .and()
            .authorizeRequests()
            .antMatchers("/apu/auth/**"
                    ,"/api/public/**"
                    ,"/api/questions/inspections/**/pages/**" //무료 검사를 위한 api 허가
            ).permitAll()
            .antMatchers("/api/member/**",
                    "/api/questions/**",
                    "/api/inspections/**",
                    "/api/answers/**"
            ).hasRole("MEMBER")
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
            ;
    }
}