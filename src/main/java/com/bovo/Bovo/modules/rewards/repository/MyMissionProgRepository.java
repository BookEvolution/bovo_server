package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.MyMissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MyMissionProgRepository extends JpaRepository<MyMissionProgress, Integer> {
    // userId로 내 미션 현황 목록 조회
    List<MyMissionProgress> findAllByUsers_Id(Integer userId);

    // 모든 user의 지난주 미션 현황 목록 조회
    List<MyMissionProgress> findAllByWeekStartDate(LocalDate lastWeekStartDate);

    // userId, missionId, weekStartDate로 미션 현황 조회
    Optional<MyMissionProgress> findByUserIdAndMissionIdAndWeekStartDate(Integer userId, Integer missionId, LocalDate WeekStartDate);

    // 특정 일자 이전 데이터 삭제
    void deleteAllByWeekStartDateBefore(LocalDate thresholdDate);
}