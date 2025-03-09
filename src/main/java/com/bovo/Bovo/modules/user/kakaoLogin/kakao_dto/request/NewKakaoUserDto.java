package com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewKakaoUserDto {
    private String profile_picture;
    private String nickname;
    private String email;
}
