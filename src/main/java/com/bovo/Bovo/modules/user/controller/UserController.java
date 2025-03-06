package com.bovo.Bovo.modules.user.controller;

import com.bovo.Bovo.modules.user.dto.request.EmailDto;
import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.NicknameDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import com.bovo.Bovo.modules.user.dto.response.JwtTokenResponseDto;
import com.bovo.Bovo.modules.user.dto.response.defResponseDto;
import com.bovo.Bovo.modules.user.service.UserService;
import com.bovo.Bovo.modules.user.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register/nickname")
    private defResponseDto verifyNickname(@RequestBody NicknameDto nicknameDto) {
        // nickname이 db에 존재하는지 확인
        if (userService.existNickname(nicknameDto.getNickname())) {
            return new defResponseDto(400, "닉네임 중복");
        }

        return new defResponseDto(201, "사용가능한 닉네임");
    }

    @PostMapping("/register/email")
    public defResponseDto verifyEmail(@RequestBody EmailDto emailDto){
        // email이 db에 존재하는지 확인
        if (userService.existEmail(emailDto.getEmail())) {
            return new defResponseDto(400, "이메일 중복");
        }

        return new defResponseDto(201, "사용가능한 이메일");
    }


    @PostMapping("/register")
    public defResponseDto signup(@RequestBody SignupDto signupDto) {
        userService.save(signupDto);
        return new defResponseDto(201, "회원가입 성공");
    }

    @PostMapping("/login")
    public JwtTokenResponseDto login(@RequestBody LoginDto loginDto, HttpServletResponse responseCookie) {
        if (!userService.existEmail(loginDto.getEmail())) {
            return new JwtTokenResponseDto(404, "존재하지 않은 사용자 이메일", null);
        }
        if (!userService.verifyLogin(loginDto)) {
            return new JwtTokenResponseDto(401, "일치하지 않는 비밀번호", null);
        }

        // 토큰 발급 로직
        String accessToken = userService.GenerateAccessToken(userService.findUserIdByEmail(loginDto.getEmail()));
        String refreshToken = userService.GenerateRefreshToken(userService.findUserIdByEmail(loginDto.getEmail()));

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600000*24*7)
                .build();
        responseCookie.setHeader("Set-Cookie", refreshTokenCookie.toString());
        return new JwtTokenResponseDto(200, "로그인 성공", accessToken);
    }

    @PostMapping("/refresh")
    public JwtTokenResponseDto refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse responseCookie) {
        Integer userId = userService.verifyRefreshToken(refreshToken);

        if (refreshToken == null || userId == 403 ) {
            return new JwtTokenResponseDto(403, "리프레쉬 토큰 만료, 재로그인 권장", null);
        }

        if (!userService.existUserIdAndRefreshToken(userId, refreshToken)) {
            return new JwtTokenResponseDto(403, "잘못된 리프레쉬 토큰, 재로그인 권장", null);
        }

        String NewAccessToken = userService.GenerateAccessToken(userId);
        String NewRefreshToken = userService.GenerateRefreshToken(userId);
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", NewRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600000*24*7)
                .build();
        responseCookie.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return new JwtTokenResponseDto(200, "엑세스 토큰 재발급 완료", NewAccessToken);
    }

    @PostMapping("/logout")
    public defResponseDto logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse responseCookie) {
        if (refreshToken != null) {
            Integer userId = userService.extractUserIdFormRefreshToken(refreshToken);
            if (userId != 403) {
                boolean delete = userService.deleteRefreshToken(userId);
            }
        }

        ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();
        responseCookie.setHeader("Set-Cookie", expiredCookie.toString());

        return new defResponseDto(200, "로그아웃 성공");
    }

    @DeleteMapping("/my-page/delete")
    public defResponseDto userdelete() {
        // 이메일로 조회해서 삭제하기
        return new defResponseDto(200, "회원 탈퇴 성공");
    }


}
