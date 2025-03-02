package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.modules.user.domain.User;
import com.bovo.Bovo.modules.user.dto.LoginDto;
import com.bovo.Bovo.modules.user.dto.SignupDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean existEmail(String email);

    boolean existNickname(String nickname);

    void save(SignupDto signupDto);

    boolean login(LoginDto loginDto);

    String JwtToken(Long userid, String SecretKey, long expireTime);

    Long findByEmail(String email);
}
