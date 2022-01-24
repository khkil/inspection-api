package com.example.backend.config.secutiry;

import com.example.backend.api.auth.redis.RedisService;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

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
        String[] freeInspectionApiList = new String[]{
                "/api/questions/inspections/**/pages/**"
                ,"/api/users/inspections/*/counts"
                ,"/api/users/answers"
                ,"/api/results"
        };

        http.httpBasic().disable()
            .csrf().disable() // csrf 보안 토큰 disable처리.
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증 이므로 세션 역시 사용x
            .and()
            .authorizeRequests()
            .antMatchers(freeInspectionApiList).permitAll()
            .antMatchers("/apu/auth/**","/api/public/**").permitAll()
            .antMatchers("/api/member/**"
                    ,"/api/questions/**"
                    ,"/api/inspections/**"
                    ,"/api/answers/**"
            ).hasRole("MEMBER")
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
            ;
    }
}