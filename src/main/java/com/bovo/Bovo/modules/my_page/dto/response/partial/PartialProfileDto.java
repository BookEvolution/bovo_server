package com.bovo.Bovo.modules.my_page.dto.response.partial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartialProfileDto {
    private String profile_picture;
    private String nickname;
    private int level;
    private int exp;
}
