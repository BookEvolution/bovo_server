package com.bovo.Bovo.modules.my_page.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewProfileUpdateDto {
    @NotNull(message = "")
    private String profile_picture;
    @NotNull(message = "")
    private String nickname;
    private String password;
}
