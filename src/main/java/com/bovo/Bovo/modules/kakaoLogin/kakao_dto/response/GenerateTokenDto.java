package com.bovo.Bovo.modules.kakaoLogin.kakao_dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerateTokenDto {
    private int status;
    private String message;
    private String accessToken;
}
