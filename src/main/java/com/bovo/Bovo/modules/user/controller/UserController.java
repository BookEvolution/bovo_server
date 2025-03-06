package com.bovo.Bovo.modules.user.controller;

import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import com.bovo.Bovo.modules.user.dto.response.JwtTokenResponseDto;
import com.bovo.Bovo.modules.user.dto.response.defResponseDto;
import com.bovo.Bovo.modules.user.service.UserService;
import com.bovo.Bovo.modules.user.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public defResponseDto signup(@RequestBody SignupDto signupDto) {

        // email이 db에 존재하는지 확인
        if (userService.existEmail(signupDto.getEmail())) {
            return new defResponseDto(400, "이미 가입된 이메일");
        }

        // nickname이 db에 존재하는지 확인
        if (userService.existNickname(signupDto.getNickname())) {
            return new defResponseDto(400, "이미 가입된 닉네임");
        }

        // 가입되지 않은 email이면 회원가입 진행
        userService.save(signupDto);
        return new defResponseDto(201, "회원가입 성공");
    }

    @PostMapping("/login")
    public JwtTokenResponseDto login(@RequestBody LoginDto loginDto, HttpServletResponse responseCookie) {
        if (!userService.existEmail(loginDto.getEmail())) {
            return new JwtTokenResponseDto(404, "존재하지 않은 사용자 이메일", null);
        }
        if (!userService.verifyLogin(loginDto)) {
            return new JwtTokenResponseDto(404, "일치하지 않는 비밀번호", null);
        }

        // 토큰 발급 로직
        String accessToken = userService.GenerateAccessToken(userService.findUserIdByEmail(loginDto.getEmail()));
        Cookie refreshToken = userService.GenerateRefreshToken(userService.findUserIdByEmail(loginDto.getEmail()));

        responseCookie.addCookie(refreshToken);
        return new JwtTokenResponseDto(200, "로그인 성공", accessToken);
    }

    @PostMapping("/refresh")
    public JwtTokenResponseDto refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse responseCookie) {
        Integer userId = userService.verifyRefreshToken(refreshToken);

        if (refreshToken == null || userId == 403) {
            return new JwtTokenResponseDto(403, "리프레쉬 토큰 만료, 재로그인 권장", null);
        }

        if (!userService.existUserIdAndRefreshToken(userId, refreshToken)) {
            return new JwtTokenResponseDto(403, "잘못된 리프레쉬 토큰, 재로그인 권장", null);
        }

        String NewAccessToken = userService.GenerateAccessToken(userId);
        Cookie NewRefreshToken = userService.GenerateRefreshToken(userId);

        responseCookie.addCookie(NewRefreshToken);
        return new JwtTokenResponseDto(200, "엑세스 토큰 재발급 완료", NewAccessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("로그아웃 되었습니다");
    }

    @DeleteMapping("/my-page/delete")
    public ResponseEntity<String> userdelete() {
        return ResponseEntity.ok("회원 탈퇴");
    }


}
