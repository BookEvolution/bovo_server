package com.bovo.Bovo.modules.user.security;

import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private final AuthenticatedUserId authenticatedUserId;

    public AuthenticationToken(AuthenticatedUserId authenticatedUserId) {
        super(null);
        this.authenticatedUserId = authenticatedUserId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUserId;
    }
}
