package com.bovo.Bovo.modules.my_page.dto.response.partial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartialProfileUpdateDto {
    private String profile_pictures;
    private String nickname;
}
