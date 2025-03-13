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
                     ReadingStatus reading_status, String recently_correction_date) {

    Users user = em.find(Users.class, user_id);
    if (user == null) {
        throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }

    MyBooks book = new MyBooks();
    book.setUsers(user);
    book.setIsbn(isbn);
    book.setBookName(book_name);
    book.setBookAuthor(book_author);
    book.setReadingStatus(reading_status);

    // ✅ 서버에서 날짜 설정 (nullable 고려)
    LocalDate startDate = (reading_start_date != null) ? LocalDate.parse(reading_start_date) : LocalDate.now();
    LocalDate correctionDate = (recently_correction_date != null) ? LocalDate.parse(recently_correction_date) : startDate;
    LocalDate endDate = (reading_end_date != null) ? LocalDate.parse(reading_end_date) : startDate; // 기본값 startDate

    book.setReadingStartDate(startDate);
    book.setRecentlyCorrectionDate(correctionDate);
    book.setReadingEndDate(endDate); // ✅ `nullable=false`이므로 반드시 값 설정

    // ✅ Nullable 처리
    book.setBookCover(book_cover);
    book.setPublicationDate(publication_date != null ? LocalDate.parse(publication_date) : null);
    book.setBookScore(BigDecimal.ZERO); // 기본값 0
    book.setBookTotalPages(book_total_pages != null ? Integer.parseInt(book_total_pages) : 0);
    book.setBookCurrentPages(book_current_pages != null ? Integer.parseInt(book_current_pages) : 0);

    em.persist(book);
}


}
