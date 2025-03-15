package com.bovo.Bovo.modules.kakaoLogin.controller;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.user.dto.response.defResponseDto;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.NewKakaoUserDto;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.response.GenerateLocalTokenDto;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.response.GenerateTokenDto;
import com.bovo.Bovo.modules.kakaoLogin.service.KakaoService;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.AuthorizationCodeDto;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.CreatedKakaoTokenDto;
import com.bovo.Bovo.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    private final UserService userService;

    @PostMapping("/kakao/login")
    public ResponseEntity<GenerateTokenDto> KakaoLogin(@RequestBody AuthorizationCodeDto authorizationCodeDto, HttpServletResponse response) {
        // 카카오 엑세스/ 리프레쉬 토큰 발급
        CreatedKakaoTokenDto createdKakaoTokenDto = kakaoService.getKakaoTokenFromKakao(authorizationCodeDto.getAuthCode());

        // 카카오 토큰 정보 보기 - 카카오 id 추출
        Long KakaoUserId = kakaoService.getKakaoIdFromKakao(createdKakaoTokenDto.getKakaoAccessToken());

        // 디비에 추출한 카카오 id를 가진 튜플 찾아서 userId 리턴
        Optional<Integer> OptionalUserId = kakaoService.ExistKakaoUserId(KakaoUserId);

        // 기존 카카오 로그인 이용자라면 토큰 저장, 응답 후 로그인 처리
        if (OptionalUserId.isPresent()) {
            Integer userId = OptionalUserId.get();

            // 로컬 토큰 발급
            GenerateLocalTokenDto generateLocalTokenDto = kakaoService.GenerateLocalToken(userId);
            // 토큰 디비 저장
            boolean SaveTokenDB = kakaoService.SaveToken(
                    createdKakaoTokenDto.getKakaoAccessToken(),
                    createdKakaoTokenDto.getKakaoRefreshToken(),
                    generateLocalTokenDto.getLocalRefreshToken(),
                    userId
                    );

            // 응답 후 로그인 처리
            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken",
                            generateLocalTokenDto.getLocalRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(3600000 * 24 * 7)
                    .build();
            response.setHeader("Set-Cookie", refreshTokenCookie.toString());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenerateTokenDto(200, "기존 카카오 이용자 로그인 성공",
                            generateLocalTokenDto.getLocalAccessToken()));
        }
        // 신규 카카오 로그인 이용자라면 Users, User_Auth 테이블 생성 및 카카오 토큰 저장,
        Users users = kakaoService.SaveNewUser();
        User_Auth userAuth = kakaoService.SaveNewToken(
                users,
                createdKakaoTokenDto.getKakaoAccessToken(),
                createdKakaoTokenDto.getKakaoRefreshToken(),
                KakaoUserId);

        kakaoService.SaveKakaoNewRewards(users);

        // 생성된 userId로 로컬 토큰 발급, 디비 저장
        Integer userId = users.getId();
        GenerateLocalTokenDto generateLocalTokenDto = kakaoService.GenerateLocalToken(userId);
        kakaoService.SaveNewLocalRefreshToken(userId, generateLocalTokenDto.getLocalRefreshToken());

        // 응답 후 로그인 처리
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken",
                        generateLocalTokenDto.getLocalRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600000 * 24 * 7)
                .build();
        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body((new GenerateTokenDto(201, "신규 카카오 이용자 로그인 성공", generateLocalTokenDto.getLocalAccessToken())));
    }

    @PostMapping("/kakao/register")
    public ResponseEntity<defResponseDto> NewKakaoLogin(@AuthenticationPrincipal AuthenticatedUserId user, @RequestBody NewKakaoUserDto newKakaoUserDto, HttpServletResponse response) {
        // nickname이 db에 존재하는지 확인
        if (userService.existNickname(newKakaoUserDto.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new defResponseDto(400, "닉네임 중복"));
        }

        // email이 db에 존재하는지 확인
        if (userService.existEmail(newKakaoUserDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new defResponseDto(400, "이메일 중복"));
        }

        // 엑세스 토큰에서 userId 추출
        Integer userId = user.getUserId();

        // userId로 조회한 디비에 회원 정보 저장
        kakaoService.SaveNewKakaoUser(newKakaoUserDto, userId);

        // 완료 응답
        return ResponseEntity.status(HttpStatus.OK)
                .body(new defResponseDto(200, "카카오 신규 회원가입 완료"));
    }
}
