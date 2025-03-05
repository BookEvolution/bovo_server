package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.common.Provider;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import com.bovo.Bovo.modules.user.repository.UserAuthRepository;
import com.bovo.Bovo.modules.user.repository.UserRepository;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtProvider jwtProvider;
    private final String SecretKey;
    private final Long expireTimeAccess;
    private final Long expireTimeRefresh;

    public UserServiceImpl(UserRepository userRepository,
                           UserAuthRepository userAuthRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtProvider jwtProvider,
                           @Value("${jwt.secretkey}") String secretKey,
                           @Value("${jwt.expiredAccessToken}") Long expireTimeAccess,
                           @Value("${jwt.expiredRefreshToken}") Long expireTimeRefresh) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
        this.SecretKey = secretKey;
        this.expireTimeAccess = expireTimeAccess;
        this.expireTimeRefresh = expireTimeRefresh;
    }

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
        Users user = new Users(signupDto.getProfile_picture(), signupDto.getNickname(), signupDto.getEmail());
        userRepository.save(user);

        User_Auth usera = new User_Auth(user, signupDto.getEmail(), bCryptPasswordEncoder.encode(signupDto.getPassword()), Provider.LOCAL);
        userAuthRepository.save(usera);
    }

    @Override
    public Long findByEmail(String email) {
        Users user = userAuthRepository.findByEmail(email)
                .get()
                .getUsers();
        return user.getId();
    }

    @Override
    public boolean verifyLogin(LoginDto loginDto) {
        User_Auth userAuth = userAuthRepository.findByEmail(loginDto.getEmail()).get();
        String foundPwd=userAuth.getPassword();
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), foundPwd)) {
            return false;
        }
        return true;
    }

    @Override
    public String GenerateAccessToken(Long userid) {
        return jwtProvider.createAccessToken(userid, SecretKey, expireTimeAccess);
    }

    @Override
    public Cookie GenerateRefreshToken(Long userid) {
        String refreshToken= jwtProvider.createRefreshToken(userid, SecretKey, expireTimeRefresh);
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(3600000*24*7);

        return refreshTokenCookie;
    }

    @Override
    public Long verifyRefreshToken(String refreshToken) {
        if (jwtProvider.ExpiredRefreshToken(refreshToken, SecretKey)==403) {
            return Long.valueOf("403");
        } else {
            return jwtProvider.ExtractUserIdFromRefreshToken(refreshToken, SecretKey);
        }
    }

}
