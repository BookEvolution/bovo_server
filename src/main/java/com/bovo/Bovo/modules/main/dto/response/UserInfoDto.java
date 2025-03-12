package com.bovo.Bovo.modules.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String profile_picture;
    private String nickname;
    private int level;
}
