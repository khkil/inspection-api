package com.example.backend.api.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails{

    private List<GrantedAuthority> authorities;
    private int idx;
    private String id;
    private String password;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String address;
    private String addressSub;
    private String school;
    private String education;
    private String grade;
    private String major;
    private String job;
    private String company;
    private String jobDetail;
    private String cDate;
    private String uDate;
    private String authKey;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public Member(String id, String name){
        this.id = id;
        this.name = name;
    }

    // UserDetails의 필수 메서드들
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}