package com.bovo.Bovo.modules.kakaoLogin.service;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.modules.kakaoLogin.repository.KakaoUserAuthRepository;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialUserDetailsService implements UserDetailsService {
    private final KakaoUserAuthRepository kakaoUserAuthRepository;

    public SocialUserDetailsService(KakaoUserAuthRepository kakaoUserAuthRepository) {
        this.kakaoUserAuthRepository = kakaoUserAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Integer userId = Integer.parseInt(username);
        Optional<User_Auth> userAuth = kakaoUserAuthRepository.findUserAuthByUserId(userId);

        if (userAuth.isEmpty()) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }

        return new AuthenticatedUserId(userId, userAuth.get().getProvider().name());
    }
}
