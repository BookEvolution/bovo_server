package com.bovo.Bovo.modules.user.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class DeleteUserDto {
    private int status;
    private String message;
    private Integer id;
}
