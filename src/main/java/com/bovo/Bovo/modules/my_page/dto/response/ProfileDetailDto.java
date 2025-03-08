package com.bovo.Bovo.modules.my_page.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileDetailDto {
    private int status;
    private String message;
    private String profile_picture;
    private String nickname;
    private String email;
    private int level;
    private String medal;
}
