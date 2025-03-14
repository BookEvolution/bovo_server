package com.bovo.Bovo.modules.kakaoLogin.service;

import com.bovo.Bovo.modules.kakaoLogin.repository.KakaoUserAuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SocialUserDetailsService implements UserDetailsService {
    private final KakaoUserAuthRepository kakaoUserAuthRepository;

    public SocialUserDetailsService(KakaoUserAuthRepository kakaoUserAuthRepository) {
        this.kakaoUserAuthRepository = kakaoUserAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return null;
    }
}
