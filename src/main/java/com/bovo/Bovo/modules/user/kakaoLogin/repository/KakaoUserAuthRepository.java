package com.bovo.Bovo.modules.user.kakaoLogin.repository;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request.NewKakaoUserDto;

import java.util.Optional;

public interface KakaoUserAuthRepository {
    Optional<User_Auth> ExistKakaoUserId(Long kakaoUserId);

    boolean SaveTokenToDB(String KakaoAccessToken, String KakaoRefreshToken, String LocalRefreshToken, Integer userId);

    void SaveNewUserToDB(Users users);

    void SaveNewTokenToDB(User_Auth userAuth);

    void SaveNewLocalRefreshTokenToDB(Integer userId, String LocalRefreshToken);

    void SaveNewKakaoUserInfo(NewKakaoUserDto newKakaoUserDto, Integer userId);
}
