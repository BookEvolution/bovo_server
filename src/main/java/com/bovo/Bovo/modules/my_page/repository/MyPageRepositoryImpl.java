package com.bovo.Bovo.modules.my_page.repository;

import com.bovo.Bovo.common.MedalType;
import com.bovo.Bovo.common.User_Auth;
import com.bovo.Bovo.common.Users;
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
                        "SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto(u.profile_picture, u.nickname, u.level, u.exp) FROM Users u WHERE u.id = :userId"
                        , PartialProfileDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public int countCompletedBooksByUserId(Integer userId) {
        return em.createQuery("SELECT count(b) FROM MyBooks b WHERE b.users.id = :userId AND b.readingStatus = 'COMPLETED'", Long.class)
                .setParameter("userId", userId)
                .getSingleResult()
                .intValue();
    }

    @Override
    public String findLastMedalByUserId(Integer userId) {
        MedalType medalType = em.createQuery("SELECT u.medalType FROM Medal u WHERE u.users.id = :userId", MedalType.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return medalType.name();
    }

    @Override
    public PartialProfileDetailDto findPartialProfileDetailByUserId(Integer userId) {
        return em.createQuery("SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto(u.profile_picture, u.nickname, u.email, u.level) FROM Users u WHERE u.id = : userId"
                        , PartialProfileDetailDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public PartialProfileUpdateDto findPartialProfileUpdateByUserId(Integer userId) {
        return em.createQuery("SELECT new com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto(u.profile_picture, u.nickname) FROM Users u WHERE u.id = :userId", PartialProfileUpdateDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public boolean newProfilePictureUpdate(String profile_picture, Integer userId) {
        System.out.println("Repository - profile_picture = " + profile_picture);
        Users users = em.find(Users.class, userId);
        if (users != null) {
            users.setProfile_picture(profile_picture);
            em.merge(users);
            return true;
        }
        return false;
    }

    @Override
    public boolean newNicknameUpdate(String nickname, Integer userId) {
        Users users = em.find(Users.class, userId);
        if (users != null) {
            users.setNickname(nickname);
            em.merge(users);
            return true;
        }
        return false;
    }

    @Override
    public boolean newPassword(String password, Integer userId) {
        User_Auth usera = em.find(User_Auth.class, userId);
        if (usera != null) {
            usera.setPassword(password);
            em.merge(usera);
            return true;
        }
        return false;
    }
}
