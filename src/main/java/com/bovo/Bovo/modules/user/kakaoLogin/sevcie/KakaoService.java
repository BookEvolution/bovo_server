package com.bovo.Bovo.modules.user.kakaoLogin.sevcie;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request.NewKakaoUserDto;
import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.response.GenerateLocalTokenDto;
import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request.CreatedKakaoTokenDto;

public interface KakaoService {
    CreatedKakaoTokenDto getKakaoToken(String code);

    Long getUserIdFromKakao(String KakaoAccessToken);

    Integer ExistKakaoUserId(Long KakaoUserId);

    GenerateLocalTokenDto GenerateLocalToken(Integer userId);

    boolean SaveToken(String KakaoAccessToken, String KakaoRefreshToken, String LocalRefreshToken, Integer userId);

    Users SaveNewUser();

    User_Auth SaveNewToken(Users users, String KakaoAccessToken, String KakaoRefreshToken, Long socialId);

    void SaveNewLocalRefreshToken(Integer userId, String LocalRefreshToken);

    void SaveNewKakaoUser(NewKakaoUserDto newKakaoUserDto, Integer userId);
}
