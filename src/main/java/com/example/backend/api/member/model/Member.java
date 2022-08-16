package com.example.backend.api.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
@DynamicUpdate
public class Member implements UserDetails{
    @Id
    @Column(name = "idx", nullable = false)
    private int idx;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private Integer groupIdx;

    private String id;

    private String password;

    private String name;

    @Column(name = "email")
    private String email;

    private String phone;
    private String address;
    private String addressSub;
    private String school;
    private String education;
    private Integer grade;
    private String major;
    private String job;
    private String company;
    private String jobDetail;
    private String cDate;
    private String uDate;
    private String delYn;

    //private String groupCode;


    public Member(String id, String name){
        this.id = id;
        this.name = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}