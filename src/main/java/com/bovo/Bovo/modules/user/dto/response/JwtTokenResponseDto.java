package com.bovo.Bovo.modules.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtTokenResponseDto {
    private int status;
    private String message;
    private String accessToken;
}
