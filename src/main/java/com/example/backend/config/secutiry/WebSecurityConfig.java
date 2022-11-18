package com.example.backend.config.secutiry;

import com.example.backend.api.auth.redis.RedisService;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] publicApiList = new String[]{
                "/api/questions/inspections/**/pages/**"
                ,"/api/users/inspections/*/counts"
                ,"/api/users/answers"
                ,"/api/results"
        };

        http.httpBasic().disable()
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증 이므로 세션 역시 사용x
            .and()
                .authorizeRequests()
                .antMatchers(publicApiList).permitAll()
                .antMatchers("/apu/auth/**","/api/public/**", "/api/inspections/**").permitAll()
                .antMatchers("/api/members/**"
                        ,"/api/questions/**"
                        ,"/api/ranks/**"
                ).hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }
}