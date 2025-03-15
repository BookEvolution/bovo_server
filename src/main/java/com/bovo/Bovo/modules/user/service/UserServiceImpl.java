package com.bovo.Bovo.modules.user.service;

import com.bovo.Bovo.common.*;
import com.bovo.Bovo.modules.user.dto.request.LoginDto;
import com.bovo.Bovo.modules.user.dto.request.SignupDto;
import com.bovo.Bovo.modules.user.repository.RewardsRepository;
import com.bovo.Bovo.modules.user.repository.UserAuthRepository;
import com.bovo.Bovo.modules.user.repository.UserRepository;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final RewardsRepository rewardsRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtProvider jwtProvider;
    private final String SecretKey;
    private final Long expireTimeAccess;
    private final Long expireTimeRefresh;

    public UserServiceImpl(UserRepository userRepository,
                           UserAuthRepository userAuthRepository,
                           RewardsRepository rewardsRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtProvider jwtProvider,
                           @Value("${jwt.secretkey}") String secretKey,
                           @Value("${jwt.expiredAccessToken}") Long expireTimeAccess,
                           @Value("${jwt.expiredRefreshToken}") Long expireTimeRefresh) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
        this.rewardsRepository = rewardsRepository;
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
        Users user = Users.createUser(signupDto.getProfile_picture(), signupDto.getNickname(), signupDto.getEmail());
        userRepository.save(user);

        User_Auth usera = User_Auth.createLocalUser(user, signupDto.getEmail(), bCryptPasswordEncoder.encode(signupDto.getPassword()));
        userAuthRepository.saveUserAuth(usera);

        Medal medal = Medal.builder()
                .users(user)
                .medalType(MedalType.NONE)
                .weekStartDate(LocalDateTime.now())
                .medalAt(LocalDateTime.now())
                .build();
        rewardsRepository.saveMedal(medal);

        List<Mission> missions = rewardsRepository.findAllMissions(); // 모든 미션 불러오기
        System.out.println("미션 개수: " + missions.size());

        for (Mission mission : missions) {
            MyMissionProgress missionProgress = MyMissionProgress.builder()
                    .users(user)
                    .mission(mission)
                    .missionCnt(0)
                    .missionAt(null)
                    .isCompleted(false)
                    .isGoalExpGiven(false)
                    .completeAt(null)
                    .weekStartDate(null)
                    .build();

            rewardsRepository.saveMyMissionProgress(missionProgress);
        }
    }

    @Override
    public Integer findUserIdByEmail(String email) {
        Users user = userAuthRepository.findUserAuthByEmail(email)
                .get()
                .getUsers();
        return user.getId();
    }

    @Override
    public boolean verifyLogin(LoginDto loginDto) {
        User_Auth userAuth = userAuthRepository.findUserAuthByEmail(loginDto.getEmail()).get();
        String foundPwd=userAuth.getPassword();
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), foundPwd)) {
            return false;
        }
        return true;
    }

    @Override
    public String GenerateAccessToken(Integer userid) {
        System.out.println("현재 사용 중인 SecretKey: " + SecretKey);
        String accessToken = jwtProvider.createAccessToken(userid, SecretKey, expireTimeAccess);
        System.out.println("accessToken: "+ accessToken);
        return accessToken;
    }

    @Override
    public String GenerateRefreshToken(Integer userid) {
        System.out.println("현재 사용 중인 SecretKey: " + SecretKey);
        String refreshToken= jwtProvider.createRefreshToken(userid, SecretKey, expireTimeRefresh);
        System.out.println("refreshToken: " + refreshToken);
        userAuthRepository.updateRefreshToken(userid, refreshToken); // DB에 리프레쉬 토큰 저장
        return refreshToken;
    }

    @Override
    public Integer verifyRefreshToken(String refreshToken) {
        if (jwtProvider.ExpiredRefreshToken(refreshToken, SecretKey)) {
            return null;
        } else {
            return jwtProvider.ExtractUserIdFromRefreshToken(refreshToken, SecretKey);
        }
    }

    @Override
    public boolean existUserIdAndRefreshToken(Integer userId, String refreshToken) {
        if (!userAuthRepository.verifyUserIdAndRefresh(userId, refreshToken)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRefreshToken(Integer userId) {
        userAuthRepository.updateRefreshToken(userId, null);
        return false;
    }

    @Override
    public Integer extractUserIdFromRefreshToken(String refreshToken) {
        return jwtProvider.ExtractUserIdFromRefreshToken(refreshToken, SecretKey);
    }

    @Override
    public Integer deleteUserByEmail(String email) {
        Users user = userAuthRepository.findUserAuthByEmail(email)
                .get()
                .getUsers();
        Integer deleteUserId = userRepository.deleteUser(user).getId();

        return deleteUserId;
    }

}
