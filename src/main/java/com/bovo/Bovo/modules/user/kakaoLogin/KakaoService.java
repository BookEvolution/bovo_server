package com.bovo.Bovo.modules.user.kakaoLogin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {
    private final KakaoConfig kakaoConfig;
    private final RestTemplate restTemplate;

    public KakaoService(KakaoConfig kakaoConfig, RestTemplate restTemplate) {
        this.kakaoConfig = kakaoConfig;
        this.restTemplate = restTemplate;
    }

    public String getKakaoAccessToken(String code) {
        try {
            String getTokenUrl = "https://kauth.kakao.com/oauth/token";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", kakaoConfig.getKakaoClientId());
            params.add("redirect_uri", kakaoConfig.getKakaoRedirectUri());
            params.add("code", code);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(getTokenUrl, request, String.class);

            return new ObjectMapper().readTree(response.getBody()).get("access_token").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

//        HTTP/1.1 200
//        Content-Type: application/json;charset=UTF-8
//        {
//            "token_type":"bearer",
//                "access_token":"${ACCESS_TOKEN}",
//                "expires_in":43199,
//                "refresh_token":"${REFRESH_TOKEN}",
//                "refresh_token_expires_in":5184000,
//                "scope":"account_email profile"
//        }
    }
}
