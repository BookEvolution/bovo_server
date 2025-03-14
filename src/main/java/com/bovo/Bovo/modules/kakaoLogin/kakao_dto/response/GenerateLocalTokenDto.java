package com.bovo.Bovo.modules.kakaoLogin.kakao_dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateLocalTokenDto {
    private String LocalAccessToken;
    private String LocalRefreshToken;
}
