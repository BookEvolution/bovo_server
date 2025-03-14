package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.Medal;
import com.bovo.Bovo.common.MedalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MedalRepository extends JpaRepository<Medal, Integer> {
    // userId로 내 훈장 조회
    Optional<Medal> findByUsers_Id(Integer userId);

    @Modifying
    @Query("UPDATE Medal m SET m.medalType = :medalType, m.weekStartDate = :weekStartDate, m.medalAt = :medalAt WHERE m.users.id = :userId")
    void updateMedalByUserId(@Param("userId") Integer userId,
                             @Param("medalType") MedalType medalType,
                             @Param("weekStartDate") LocalDate weekStartDate,
                             @Param("medalAt") LocalDateTime medalAt);
}