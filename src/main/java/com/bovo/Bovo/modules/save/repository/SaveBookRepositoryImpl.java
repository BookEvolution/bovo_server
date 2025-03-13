package com.bovo.Bovo.modules.save.repository;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingStatus;
import com.bovo.Bovo.common.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Repository
@Transactional
public class SaveBookRepositoryImpl implements SaveBookRepository {
    private final EntityManager em;

    public SaveBookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    // ✅ ISBN + 사용자 ID로 도서 조회 (MyBooks 엔티티 자체 반환)
    @Override
public MyBooks findBookByIsbnAndUserId(String isbn, Integer user_id) {
    try {
        System.out.println("[DEBUG] findBookByIsbnAndUserId() 호출됨: user_id = " + user_id + ", isbn = " + isbn);

        return em.createQuery(
                "SELECT b FROM MyBooks b WHERE b.isbn = :isbn AND b.users.id = :user_id", // ✅ MyBooks 엔티티를 직접 조회
                MyBooks.class
            )
            .setParameter("isbn", isbn)
            .setParameter("user_id", user_id)  // ✅ user_id 바인딩 확인
            .getSingleResult();
    } catch (NoResultException e) {
        System.out.println("[DEBUG] 도서를 찾을 수 없음 (isbn: " + isbn + ", user_id: " + user_id + ")");
        return null;
    }
}


    // ✅ ISBN + 사용자 ID로 중복 확인
    @Override
    public boolean existsByIsbnAndUserId(String isbn, Integer user_id) {
        System.out.println("[DEBUG] existsByIsbnAndUserId() 호출됨: user_id = " + user_id + ", isbn = " + isbn);

        Long count = em.createQuery(
                        "SELECT COUNT(b) FROM MyBooks b WHERE b.isbn = :isbn AND b.users.id = :user_id", // ✅ MyBooks 엔티티 기반 쿼리 수정
                        Long.class
                )
                .setParameter("isbn", isbn)
                .setParameter("user_id", user_id)
                .getSingleResult();
        return count > 0;
    }


    // ✅ 도서 저장
    @Override
public void saveBook(Integer user_id, String isbn, String book_name, String book_author, String book_cover,
                     String publication_date, String reading_start_date, String reading_end_date,
                     String book_score, String book_total_pages, String book_current_pages,
                     ReadingStatus reading_status, String recently_correction_date) { // ✅ Enum 타입 유지

    Users user = em.find(Users.class, user_id); // 사용자 정보 조회
    if (user == null) {
        throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }

    MyBooks book = new MyBooks();
    book.setUsers(user);
    book.setIsbn(isbn);
    book.setBookName(book_name);
    book.setBookAuthor(book_author);
    book.setBookCover(book_cover);
    book.setPublicationDate(publication_date != null ? LocalDate.parse(publication_date) : null);
    book.setReadingStartDate(LocalDate.parse(reading_start_date));
    book.setReadingEndDate(LocalDate.parse(reading_end_date));
    book.setBookScore(new BigDecimal(book_score));
    book.setBookTotalPages(Integer.parseInt(book_total_pages));
    book.setBookCurrentPages(Integer.parseInt(book_current_pages));
    book.setReadingStatus(reading_status); // ✅ DTO에서 변환된 Enum 그대로 저장
    book.setRecentlyCorrectionDate(LocalDate.parse(recently_correction_date));

    em.persist(book); // ✅ DB에 저장
}

}
