package com.example.backend.api.member.model;

import com.example.backend.util.enumerator.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ogs_member")
@DynamicUpdate
public class Member implements UserDetails{
    @Id
    @Column(name = "idx", nullable = false)
    private int idx;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Column(name = "c_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date cDate;

    private String uDate;

    private String delYn;

    //private String groupCode;


    public Member(String id, String name){
        this.id = id;
        this.name = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        if(role != null){
            collect.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return collect;
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