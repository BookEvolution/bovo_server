package com.bovo.Bovo.modules.archive.repository;

import com.bovo.Bovo.common.MyBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyBooksRepository extends JpaRepository<MyBooks, Long> {
    // userId 컬럼(또는 연관된 User 엔티티의 id)으로 등록된 책 목록을 조회
    List<MyBooks> findByUserId(Long userId);
}

