package com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedKakaoTokenDto {
    private String KakaoAccessToken;
    private String KakaoRefreshToken;
}
