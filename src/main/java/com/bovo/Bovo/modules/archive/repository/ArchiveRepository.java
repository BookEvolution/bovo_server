package com.bovo.Bovo.modules.archive.repository;

import com.bovo.Bovo.common.MyBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<MyBooks, Integer> {
    // userId로 내 서재 조회
    List<MyBooks> findByUsers_Id(Integer userId);
}