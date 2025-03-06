package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import jakarta.servlet.http.Cookie;

public interface UserService {
    boolean existEmail(String email);

    boolean existNickname(String nickname);

    void save(SignupDto signupDto);

    Integer findUserIdByEmail(String email);

    boolean verifyLogin(LoginDto loginDto);

    String GenerateAccessToken(Integer userid);

    Cookie GenerateRefreshToken(Integer userid);

    Integer verifyRefreshToken(String refreshToken);

    boolean existUserIdAndRefreshToken(Integer userId, String RefreshToken);

}
