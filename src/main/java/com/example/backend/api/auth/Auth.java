package com.example.backend.api.auth;

import com.example.backend.api.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auth {

    private String accessToken;
    private String refreshToken;
    private Member member;

}
