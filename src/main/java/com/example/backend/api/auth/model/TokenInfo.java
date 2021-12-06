package com.example.backend.api.auth.model;

import com.example.backend.api.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenInfo {

    public TokenInfo(String accessToken, Member member){
        this.accessToken = accessToken;
        this.member = member;
    }

    private String accessToken;
    private String refreshToken;
    private Date expiredDate;
    private Member member;

}

