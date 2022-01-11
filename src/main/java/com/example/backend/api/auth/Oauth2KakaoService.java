package com.example.backend.api.auth;

import com.example.backend.api.auth.model.AuthKakao;
import com.example.backend.common.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class Oauth2KakaoService {

    private final RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Value("${kakao.client_id}")
    private String kakaoClientId;
    @Value("${kakao.redirect_url}")
    private String redirectUrl;
    private String grantType = "authorization_code";
    private String url = "https://kauth.kakao.com/oauth/token";

    public Oauth2KakaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AuthKakao callTokenApi(String code){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", redirectUrl + "/callback/kakao");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            AuthKakao authorization = objectMapper.readValue(response.getBody(), AuthKakao.class);

            return authorization;
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            throw new ApiException("로그인 실패");
        }

    }

}
