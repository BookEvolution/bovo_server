package com.bovo.Bovo.modules.user.kakaoLogin.repository;

import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.user.kakaoLogin.kakao_dto.request.NewKakaoUserDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.socialId= :kakaoUserId", User_Auth.class)
                .setParameter("kakaoUserId", kakaoUserId)
                .getSingleResult();
        return Optional.ofNullable(usera); // db에 email이 존재하면 1, 존재하지 않으면 0
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
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.users.id= :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        if (usera != null) {
            usera.setRefreshToken(LocalRefreshToken);
        }
    }

    @Override
    public void SaveNewKakaoUserInfo(NewKakaoUserDto newKakaoUserDto, Integer userId) {
        Users users = em.createQuery("SELECT u FROM Users u WHERE u.id = :userId", Users.class)
                .setParameter("userId", userId)
                .getSingleResult();
        if (users != null) {
            users.setProfile_picture(newKakaoUserDto.getProfile_picture());
            users.setNickname(newKakaoUserDto.getNickname());
            users.setEmail(newKakaoUserDto.getEmail());
        }
    }

}
