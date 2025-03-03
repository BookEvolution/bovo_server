package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.modules.user.dto.LoginDto;
import com.bovo.Bovo.modules.user.dto.SignupDto;
import jakarta.servlet.http.Cookie;

public interface UserService {
    boolean existEmail(String email);

    boolean existNickname(String nickname);

    void save(SignupDto signupDto);

    boolean verifyLogin(LoginDto loginDto);

    String GenerateAccessToken(Long userid);

    Cookie GenerateRefreshToken(Long userid);

    Long findByEmail(String email);
}
