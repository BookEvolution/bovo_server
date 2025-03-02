package com.bovo.Bovo.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull(message = "")
    @Email(message = "")
    private String email;

    @NotNull(message = "")
    private String password;
}
