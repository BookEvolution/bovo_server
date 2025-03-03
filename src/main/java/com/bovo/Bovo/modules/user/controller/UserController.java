package com.bovo.Bovo.modules.user.controller;

import com.bovo.Bovo.modules.user.dto.LoginDto;
import com.bovo.Bovo.modules.user.dto.SignupDto;
import com.bovo.Bovo.modules.user.service.UserServiceImpl;
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
    private final UserServiceImpl userService;

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
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
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
        String JWT = userService.GenerateJwtToken(userService.findByEmail(loginDto.getEmail()));
        // 쿠키에 JWT 담아 전달


        Map<String, Object> response = new HashMap<>();
        response.put("status", 20);
        response.put("message", "로그인 성공");
        return ResponseEntity.ok(response);
    }
}
