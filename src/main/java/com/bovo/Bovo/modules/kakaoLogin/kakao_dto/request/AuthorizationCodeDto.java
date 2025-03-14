package com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationCodeDto {
    private String authCode;
}
