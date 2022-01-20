package com.example.backend.api.auth;

import com.example.backend.api.auth.model.AuthKakao;
import com.example.backend.util.HttpUrlConnectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class Oauth2NaverService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HttpUrlConnectionUtil httpUrlConnectionUtil;


    @Value("${naver.client_id}")
    private String clientId;
    @Value("${naver.client_secret}")
    private String clientSecret;
    private String redirectUrl = "http://localhost:3000/auth/login/naver";


    public Map<String, Object> callTokenApi(String code){

        SecureRandom random = new SecureRandom();
        String requestUrl = "https://nid.naver.com/oauth2.0/token";
        String state = new BigInteger(130, random).toString();

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUrl);
        params.put("code", code);
        params.put("state", state);

        try {
            String response = httpUrlConnectionUtil.requestToServer(requestUrl, HttpMethod.POST, params, null);
            Map<String, Object> authorization = objectMapper.readValue(response, Map.class);
            return authorization;

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> callUserByAccessToken(String accessToken){

        String requestUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +accessToken);
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
