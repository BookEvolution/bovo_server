package com.bovo.Bovo.modules.user.dto.security;

public class AuthenticatedUserId {
    private final Integer userId;

    public AuthenticatedUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
