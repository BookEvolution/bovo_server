package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.User_Auth;

import java.util.Optional;

public interface UserAuthRepository {
    boolean existEmail(String email);

    User_Auth saveUserAuth(User_Auth usera); // 회원가입: usera를 DB에 저장

    Optional<User_Auth> findUserAuthByEmail(String email); // email로 user 조회

    boolean verifyUserIdAndRefresh(Integer userId, String refreshToken); // userid로 user 조회

    void updateRefreshToken(Integer userId, String refreshToken);

    boolean deleteRefreshToken(Integer userId);
}
