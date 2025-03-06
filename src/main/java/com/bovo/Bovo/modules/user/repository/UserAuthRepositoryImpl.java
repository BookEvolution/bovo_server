package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.User_Auth;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class UserAuthRepositoryImpl implements UserAuthRepository{
    private final EntityManager em;

    public UserAuthRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean existEmail(String email) {
        Long num = em.createQuery("SELECT count(u) FROM User_Auth u WHERE u.email= :email AND u.provider='LOCAL'", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return num>0; // db에 email이 존재하면 1, 존재하지 않으면 0
    }

    @Override
    public User_Auth saveUserAuth(User_Auth usera) {
        em.persist(usera);
        return usera;
    }

    @Override
    public Optional<User_Auth> findUserAuthByEmail(String email) { // JPQL을 이용한 email에 해당하는 user 조회
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.email= :email", User_Auth.class)
                .setParameter("email", email)
                .getSingleResult(); // 카카오 로그인 추가 시 수정 필요
        return Optional.ofNullable(usera);
    }

    @Override
    public boolean verifyUserIdAndRefresh(Integer userId, String refreshToken) {
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.user.id= :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        boolean verify = usera.getRefresh_token().equals(refreshToken);
        if (!verify) {
            return false;
        }
        return true;
    }

    @Override
    public void updateRefreshToken(Integer userId, String refreshToken) {
        User_Auth usera = em.createQuery("SELECT u FROM User_Auth u WHERE u.user.id= :userId", User_Auth.class)
                .setParameter("userId", userId)
                .getSingleResult();
        if (usera != null) {
            usera.setRefresh_token(refreshToken);
        }
    }

    @Override
    public boolean deleteRefreshToken(Integer userId) {
        return false;
    }
}