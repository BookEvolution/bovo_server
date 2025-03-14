package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.MyMissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MyMissionProgRepository extends JpaRepository<MyMissionProgress, Integer> {
    // userId로 내 미션 현황 조회
    List<MyMissionProgress> findAllByUsers_Id(Integer userId);

    // 모든 user의 지난 주 미션 현황 조회
    List<MyMissionProgress> findAllByWeekStartDate(LocalDate weekStartDate);
}