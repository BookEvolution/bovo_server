package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.MyMissionProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyMissionProgRepository extends JpaRepository<MyMissionProgress, Integer> {

    // userId로 내 미션 현황 조회
    List<MyMissionProgress> findByUsers_Id(Integer userId);
}

