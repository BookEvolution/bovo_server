package com.bovo.Bovo.modules.kakaoLogin.repository;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.kakaoLogin.kakao_dto.request.NewKakaoUserDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class KakaoUserAuthRepositoryImpl implements KakaoUserAuthRepository {
    private final EntityManager em;

    public KakaoUserAuthRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User_Auth> ExistKakaoUserId(Long kakaoUserId) {
        List<User_Auth> userAuthList = em.createQuery("SELECT u FROM User_Auth u WHERE u.socialId= :kakaoUserId", User_Auth.class)
                .setParameter("kakaoUserId", kakaoUserId)
                .getResultList();
        return userAuthList.isEmpty() ? Optional.empty() : Optional.of(userAuthList.get(0));
    }

    @Override
    public boolean SaveTokenToDB(String KakaoAccessToken, String KakaoRefreshToken, String LocalRefreshToken, Integer userId) {
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id= :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        if (usera != null) {
            usera.setRefreshToken(LocalRefreshToken);
            usera.setKakaoAccessToken(KakaoAccessToken);
            usera.setKakaoRefreshToken(KakaoRefreshToken);
        }

        return true;
    }

    @Override
    public void SaveNewUserToDB(Users users) {
        em.persist(users);
    }

    @Override
    public void SaveNewTokenToDB(User_Auth userAuth) {
        em.persist(userAuth);
    }

    @Override
    public void SaveNewLocalRefreshTokenToDB(Integer userId, String LocalRefreshToken) {
        List<User_Auth> userAuthList = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id= :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getResultList();

        User_Auth usera = userAuthList.isEmpty() ? null : userAuthList.get(0);

        if (usera != null) {
            usera.setRefreshToken(LocalRefreshToken);
        }
    }

    @Override
    public void SaveNewKakaoUserInfo(NewKakaoUserDto newKakaoUserDto, Integer userId) {
        Users users = em.createQuery("SELECT u FROM Users u WHERE u.id = :userId", Users.class)
                .setParameter("userId", userId)
                .getSingleResult();

        User_Auth userAuth = em.createQuery("SELECT ua FROM User_Auth ua WHERE ua.users.id = :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();

        if (users != null) {
            users.setProfile_picture(newKakaoUserDto.getProfile_picture());
            users.setNickname(newKakaoUserDto.getNickname());
            users.setEmail(newKakaoUserDto.getEmail());
        }

        if (userAuth != null) {
            userAuth.setEmail(newKakaoUserDto.getEmail());
        }
    }

    @Override
    public Optional<User_Auth> findUserAuthByUserId(Integer userId) {
        User_Auth userAuth = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id = :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return Optional.ofNullable(userAuth);
    }

    @Override
    public String getKakaoAccessTokenByUserId(Integer userId) {
        String KakaoAccessToken = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id = :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult()
                .getKakaoAccessToken();
        return KakaoAccessToken;
    }

    @Override
    public boolean deleteKakaoTokenForLogout(Integer userId) {
        User_Auth userAuth = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id = :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        userAuth.setKakaoAccessToken(null);
        userAuth.setKakaoRefreshToken(null);
        return true;
    }

}
