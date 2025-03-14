package com.bovo.Bovo.modules.save.repository;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingStatus;

public interface SaveBookRepository {
    MyBooks findBookByIsbnAndUserId(String isbn, Integer user_id); // ✅ 특정 사용자 & ISBN 조회
    boolean existsByIsbnAndUserId(String isbn, Integer user_id); // ✅ 중복 확인
    void saveBook(Integer user_id, String isbn, String book_name, String book_author, String book_cover, String publication_date,
                  String reading_start_date, String reading_end_date, String book_score, String book_total_pages,
                  String book_current_pages, ReadingStatus reading_status, String recently_correction_date); // ✅ 도서 저장
}
