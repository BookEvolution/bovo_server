package com.bovo.Bovo.modules.user.dto.security;

import com.bovo.Bovo.common.Provider;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class AuthenticatedUserId implements UserDetails {
    private final Integer userId;
    private final String provider;

    public AuthenticatedUserId(Integer userId, String provider) {
        this.userId = userId;
        this.provider = provider;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getProvider() {
        return provider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }
}
