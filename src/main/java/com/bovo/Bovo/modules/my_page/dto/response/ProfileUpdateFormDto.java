package com.bovo.Bovo.modules.my_page.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileUpdateFormDto {
    private int status;
    private String message;
    private String profile_pictures;
    private String nickname;
}
