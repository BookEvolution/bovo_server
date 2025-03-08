package com.bovo.Bovo.modules.my_page.repository;

import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class MyPageRepositoryImpl implements MyPageRepository {
    private final EntityManager em;

    public MyPageRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public PartialProfileDto findPartialByUserId(Integer userId) {
        return em.createQuery(
                        "SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfile(u.profile_picture, u.nickname, u.level, u.exp) FROM users u WHERE u.id = : userId"
                        , PartialProfileDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public int countCompletedBooksByUserId(Integer userId) {
        return em.createQuery("SELECT count(b) FROM my_books b WHERE b.users.id = :userId AND b.reading_status = 'COMPLETED'", Long.class)
                .setParameter("userId", userId)
                .getSingleResult()
                .intValue();
    }

    @Override
    public String findLastMedalByUserId(Integer userId) {
        return em.createQuery("SELECT u.medal_type FROM medal u WHERE u.users.id = :userId", String.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public PartialProfileDetailDto findPartialProfileDetailByUserId(Integer userId) {
        return em.createQuery("SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto(u.profile_picture, u.nickname, u.email, u.level) FROM users u WHERE u.id = : userId"
                        , PartialProfileDetailDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public PartialProfileUpdateDto findPartialProfileUpdateByUserId(Integer userId) {
        return em.createQuery("SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto(u.profile_picture, u.nickname) FROM users u WHERE u.id = :userId", PartialProfileUpdateDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
