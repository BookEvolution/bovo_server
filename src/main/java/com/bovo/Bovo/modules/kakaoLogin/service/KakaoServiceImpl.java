package com.bovo.Bovo.modules.kakaoLogin.service;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.NewKakaoUserDto;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.response.GenerateLocalTokenDto;
import com.bovo.Bovo.modules.kakaoLogin.config.KakaoConfig;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.CreatedKakaoTokenDto;
import com.bovo.Bovo.modules.kakaoLogin.repository.KakaoUserAuthRepository;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class KakaoServiceImpl implements KakaoService {
    private final KakaoConfig kakaoConfig;
    private final RestTemplate restTemplate;
    private final KakaoUserAuthRepository kakaoUserAuthRepository;
    private final JwtProvider jwtProvider;

    private final String SecretKey;
    private final Long expireTimeAccess;
    private final Long expireTimeRefresh;

    public KakaoServiceImpl(KakaoConfig kakaoConfig,
                            RestTemplate restTemplate,
                            KakaoUserAuthRepository kakaoUserAuthRepository,
                            JwtProvider jwtProvider,
                            @Value("${jwt.secretkey}") String secretKey,
                            @Value("${jwt.expiredAccessToken}") Long expireTimeAccess,
                            @Value("${jwt.expiredRefreshToken}") Long expireTimeRefresh
    ) {
        this.kakaoConfig = kakaoConfig;
        this.restTemplate = restTemplate;
        this.kakaoUserAuthRepository = kakaoUserAuthRepository;
        this.jwtProvider = jwtProvider;
        this.SecretKey = secretKey;
        this.expireTimeAccess = expireTimeAccess;
        this.expireTimeRefresh = expireTimeRefresh;
    }

    public CreatedKakaoTokenDto getKakaoTokenFromKakao(String code) {
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

        try {
            JsonNode tokenResponse = new ObjectMapper().readTree(response.getBody());
            return CreatedKakaoTokenDto.builder()
                    .KakaoAccessToken(tokenResponse.get("access_token").asText())
                    .KakaoRefreshToken(tokenResponse.get("refresh_token").asText())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("카카오 토큰 파싱 오류", e);
        }
    }

    public Long getKakaoIdFromKakao(String KakaoAccessToken) {
        String tokenInfoUrl = "https://kapi.kakao.com/v1/user/access_token_info";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + KakaoAccessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenInfoUrl, HttpMethod.GET, request, String.class);

        try {
            return new ObjectMapper().readTree(response.getBody()).get("id").asLong();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("카카오 토큰 정보 조회 오류", e);
        }
    }

    @Override
    public Optional<Integer> ExistKakaoUserId(Long KakaoUserId) {
        return kakaoUserAuthRepository.ExistKakaoUserId(KakaoUserId)
                .map(userAuth -> userAuth.getUsers().getId());
    }

    @Override
    public GenerateLocalTokenDto GenerateLocalToken(Integer userId) {
        return new GenerateLocalTokenDto(
                jwtProvider.createAccessToken(userId, SecretKey, expireTimeAccess),
                jwtProvider.createRefreshToken(userId, SecretKey, expireTimeRefresh)
        );
    }

    @Override
    public boolean SaveToken(String KakaoAccessToken, String KakaoRefreshToken, String LocalRefreshToken, Integer userId) {
        return kakaoUserAuthRepository.SaveTokenToDB(KakaoAccessToken, KakaoRefreshToken, LocalRefreshToken, userId);
    }

    @Override
    public Users SaveNewUser() {
        Users users = Users.builder()
                .profile_picture(null)
                .nickname(null)
                .email(null)
                .join_date(LocalDateTime.now())
                .build();
        System.out.println("생성된 User: " + users);
        System.out.println("User의 join_date: " + users.getJoin_date());
        kakaoUserAuthRepository.SaveNewUserToDB(users);
        return users;
    }

    @Override
    public User_Auth SaveNewToken(Users users, String KakaoAccessToken, String KakaoRefreshToken, Long socialId) {
        User_Auth userAuth = User_Auth.createKakaoUser(users, socialId, KakaoAccessToken, KakaoRefreshToken);
        kakaoUserAuthRepository.SaveNewTokenToDB(userAuth);
        return userAuth;
    }

    @Override
    public void SaveNewLocalRefreshToken(Integer userId, String LocalRefreshToken) {
        kakaoUserAuthRepository.SaveNewLocalRefreshTokenToDB(userId, LocalRefreshToken);
    }

    @Override
    public void SaveNewKakaoUser(NewKakaoUserDto newKakaoUserDto, Integer userId) {
        kakaoUserAuthRepository.SaveNewKakaoUserInfo(newKakaoUserDto, userId);
    }

    @Override
    public void logoutFromKakao(String KakaoAccessToken) {
        String logoutKakaoUrl = "https://kapi.kakao.com/v1/user/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + KakaoAccessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(logoutKakaoUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("카카오 사용자 로그아웃 성공");
        }else {
            System.out.println("카카오 사용자 로그아웃 실패" + response.getStatusCode());
        }
    }

    @Override
    public String findKakaoUserByUserId(Integer userId) {
        return kakaoUserAuthRepository.findUserAuthByUserId(userId)
                .get()
                .getProvider()
                .name();
    }

    @Override
    public String getKakaoAccessToken(Integer userId) {
        return kakaoUserAuthRepository.getKakaoAccessTokenByUserId(userId);
    }

    @Override
    public boolean deleteKakaoTokenForLogout(Integer userId) {
        return kakaoUserAuthRepository.deleteKakaoTokenForLogout(userId);
    }


}
