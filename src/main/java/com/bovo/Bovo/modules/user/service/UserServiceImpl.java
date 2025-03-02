package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.modules.user.domain.Provider;
import com.bovo.Bovo.modules.user.domain.User;
import com.bovo.Bovo.modules.user.domain.UserAuth;
import com.bovo.Bovo.modules.user.dto.LoginDto;
import com.bovo.Bovo.modules.user.dto.SignupDto;
import com.bovo.Bovo.modules.user.repository.UserAuthRepository;
import com.bovo.Bovo.modules.user.repository.UserRepository;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtProvider jwtProvider;
    private String SecretKey;

    @Override
    public boolean existEmail(String email) {
        boolean exist = userAuthRepository.existEmail(email);
        return exist;
    }

    @Override
    public boolean existNickname(String nickname) {
        boolean exist = userRepository.existNickname(nickname);
        return exist;
    }

    @Override
    public void save(SignupDto signupDto) {
        User user = new User(signupDto.getProfile_picture(), signupDto.getNickname(), signupDto.getEmail());
        userRepository.save(user);

        UserAuth usera = new UserAuth(user, signupDto.getEmail(), bCryptPasswordEncoder.encode(signupDto.getPassword()), Provider.LOCAL);
        userAuthRepository.save(usera);
    }

    @Override
    public boolean login(LoginDto loginDto) {
        UserAuth userAuth = userAuthRepository.findByEmail(loginDto.getEmail()).get();
        String foundPwd=userAuth.getPassword();
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), foundPwd)) {
            return false;
        }
        return true;
    }

    @Override
    public String JwtToken(Long userid, String SecretKey, long expireTime) {
        return "";
    }

    @Override
    public Long findByEmail(String email) {
        User user = userAuthRepository.findByEmail(email)
                .get()
                .getUser();
        return user.getUserid();
    }
}
