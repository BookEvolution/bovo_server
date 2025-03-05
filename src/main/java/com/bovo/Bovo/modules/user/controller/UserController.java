package com.bovo.Bovo.modules.user.controller;

import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
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
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupDto signupDto) {

        // email이 db에 존재하는지 확인
        if (userService.existEmail(signupDto.getEmail())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 400);
            response.put("message", "이미 가입된 이메일");
            return ResponseEntity.ok(response);
        }

        // nickname이 db에 존재하는지 확인
        if (userService.existNickname(signupDto.getNickname())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 400);
            response.put("message", "이미 가입된 닉네임");
            return ResponseEntity.ok(response);
        }

        // 가입되지 않은 email이면 회원가입 진행
        userService.save(signupDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "회원가입 성공");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto, HttpServletResponse responseCookie) {
        if (!userService.existEmail(loginDto.getEmail())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 404);
            response.put("message", "존재하지 않은 사용자 이메일");
            return ResponseEntity.ok(response);
        }
        if (!userService.verifyLogin(loginDto)) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 401);
            response.put("message", "일치하지 않는 비밀번호");
            return ResponseEntity.ok(response);
        }

        // 토큰 발급 로직
        String accessToken = userService.GenerateAccessToken(userService.findByEmail(loginDto.getEmail()));
        Cookie refreshToken = userService.GenerateRefreshToken(userService.findByEmail(loginDto.getEmail()));

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "로그인 성공");
        response.put("accessToken", accessToken);
        responseCookie.addCookie(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        Long verify = userService.verifyRefreshToken(refreshToken);

        if (refreshToken == null || verify == Long.parseLong("403")) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 403);
            response.put("message", "리프레쉬 토큰 만료, 재로그인 권장");
            return ResponseEntity.ok(response);
        }
        String accessToken = userService.GenerateAccessToken(verify);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "엑세스 토큰 재발급 완료");
        response.put("accessToken", accessToken);
        return ResponseEntity.ok(response);
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
