package com.bovo.Bovo.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    @NotNull(message = "")
    private String profile_picture;

    @NotNull(message = "")
    private String nickname;

    @NotNull(message = "")
    @Email(message = "")
    private String email;

    @NotNull(message = "")
    private String password;

    @NotNull(message = "")
    private String pwd_check;
}
