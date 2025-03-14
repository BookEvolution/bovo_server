package com.bovo.Bovo.modules.user.dto.security;

import lombok.Getter;

@Getter
public class AuthenticatedUserId {
    private final Integer userId;

    public AuthenticatedUserId(Integer userId) {
        this.userId = userId;
    }

}
