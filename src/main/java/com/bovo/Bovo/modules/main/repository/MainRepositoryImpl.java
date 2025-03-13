package com.bovo.Bovo.modules.main.repository;

import com.bovo.Bovo.modules.main.dto.response.BookListDto;
import com.bovo.Bovo.modules.main.dto.response.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.UserInfoDto;
import com.bovo.Bovo.modules.main.dto.response.partial.BookNameDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@Transactional
public class MainRepositoryImpl implements MainRepository {
    private final EntityManager em;

    public MainRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public UserInfoDto getUserInfoByUserId(Integer userId) {
        return em.createQuery(
                        "SELECT new com.bovo.Bovo.modules.main.dto.response.UserInfoDto(u.profile_picture, u.nickname, u.level) FROM Users u WHERE u.id = :userId"
                        , UserInfoDto.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public RecentBookInfoDto getRecentBookInfoByUserId(Integer userId) {
        try {
            return em.createQuery("SELECT new com.bovo.Bovo.modules.main.dto.response.RecentBookInfoDto(b.bookName, b.bookAuthor, b.bookCover, b.readingStartDate, b.bookScore) FROM MyBooks b WHERE b.users.id = :userId AND b.readingStatus = 'READING' ORDER BY b.recentlyCorrectionDate DESC"
                            , RecentBookInfoDto.class)
                    .setParameter("userId", userId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public BookListDto getBookListByUserId(Integer userId) {
        List<BookNameDto> book = em.createQuery("SELECT new com.bovo.Bovo.modules.main.dto.response.partial.BookNameDto(b.bookName) FROM MyBooks b WHERE b.users.id=:userId AND b.readingStatus = 'COMPLETED' ORDER BY b.recentlyCorrectionDate DESC"
                        , BookNameDto.class)
                .setParameter("userId", userId)
                .setMaxResults(6)
                .getResultList();

        Map<String, String> bookMapping = new HashMap<>();
        for (int i = 0; i < book.size(); i++) {
            bookMapping.put(String.valueOf("book_name_" + (i + 1)), book.get(i).getBookName());
        }

        return new BookListDto(bookMapping);
    }
}
