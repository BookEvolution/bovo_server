package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import jakarta.servlet.http.Cookie;

public interface UserService {
    boolean existEmail(String email);

    boolean existNickname(String nickname);

    void save(SignupDto signupDto);

    Long findByEmail(String email);

    boolean verifyLogin(LoginDto loginDto);

    String GenerateAccessToken(Long userid);

    Cookie GenerateRefreshToken(Long userid);

    Long verifyRefreshToken(String refreshToken);

}
