package com.example.backend.api.auth;

import com.example.backend.api.auth.model.AuthKakao;
import com.example.backend.util.HttpUrlConnectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class Oauth2KakaoService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HttpUrlConnectionUtil httpUrlConnectionUtil;

    @Value("${kakao.client_id}")
    private String kakaoClientId;
    @Value("${kakao.redirect_url}")
    private String redirectUrl;
    private String grantType = "authorization_code";


    public Oauth2KakaoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AuthKakao callTokenApi(String code) {

        String requestUrl = "https://kauth.kakao.com/oauth/token";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", grantType);
        params.put("client_id", kakaoClientId);
        params.put("redirect_uri", redirectUrl);
        params.put("code", code);

        try {
            String response = httpUrlConnectionUtil.requestToServer(requestUrl, HttpMethod.POST, params, null);
            AuthKakao authorization = objectMapper.readValue(response, AuthKakao.class);
            return authorization;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> callUserByAccessToken(String accessToken){

        String requestUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            String response = httpUrlConnectionUtil.requestToServer(requestUrl, HttpMethod.POST, null, headers);
            Map<String, Object> accessTokenInfo = objectMapper.readValue(response, Map.class);
            return accessTokenInfo;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
