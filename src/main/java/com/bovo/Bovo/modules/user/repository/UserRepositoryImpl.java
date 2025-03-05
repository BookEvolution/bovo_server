package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.Users;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Users save(Users user) { // user를 DB(users table)에 저장
        em.persist(user);
        return user;
    }

    @Override
    public boolean existNickname(String nickname) {
        Long num = em.createQuery("SELECT count(user) FROM Users user WHERE user.nickname= :nickname", Long.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
        return num>0; // db에 nickname이 존재하면 1, 존재하지 않으면 0
    }
}
