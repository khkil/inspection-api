package com.example.backend.api.auth;

import com.example.backend.common.exception.UserAuthorityException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

@Service
public class Oauth2NaverService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;


    @Value("${naver.client_id}")
    private String clientId;
    @Value("${naver.client_secret}")
    private String clientSecret;
    @Value("${naver.redirect_url}")
    private String redirectUrl;


    public Map<String, Object> callTokenApi(String code){

        String requestUrl = "https://nid.naver.com/oauth2.0/token";
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUrl);
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
            Map<String, Object> authorization = objectMapper.readValue(response.getBody(), Map.class);
            return authorization;

        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            throw new UserAuthorityException("네이버 로그인 실패");
        }
    }

    public Map<String, Object> callUserByAccessToken(String accessToken){

        String requestUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
            Map<String, Object> tokenInfo = objectMapper.readValue(response.getBody(), Map.class);
            return tokenInfo;

        }catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            throw new UserAuthorityException("네이버 로그인 유저 불러오기 실패");
        }
    }
}
