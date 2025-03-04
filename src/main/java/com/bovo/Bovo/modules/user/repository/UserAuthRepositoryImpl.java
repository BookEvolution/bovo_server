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
        Long num = em.createQuery("SELECT count(usera) FROM User_Auth usera WHERE usera.email= :email AND usera.provider='LOCAL'", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return num>0; // db에 email이 존재하면 1, 존재하지 않으면 0
    }

    @Override
    public User_Auth save(User_Auth usera) {
        em.persist(usera);
        return usera;
    }

    @Override
    public Optional<User_Auth> findByEmail(String email) { // JPQL을 이용한 email에 해당하는 user 조회
        User_Auth usera = em.createQuery("SELECT u FROM UserAuth u WHERE u.email= :email", User_Auth.class)
                .setParameter("email", email)
                .getSingleResult(); // 카카오 로그인 추가 시 수정 필요
        return Optional.ofNullable(usera);
    }
}
