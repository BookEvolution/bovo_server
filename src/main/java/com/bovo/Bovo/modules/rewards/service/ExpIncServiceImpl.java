package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.rewards.repository.MissionRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import com.bovo.Bovo.modules.rewards.repository.RewardsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class ExpIncServiceImpl implements ExpIncService {

    private final RewardsUserRepository rewardsUserRepository;
    private final MissionRepository missionRepository;
    private final MyMissionProgRepository myMissionProgRepository;

    @Override
    @Transactional
    public void updateExp(Integer userId, Integer missionId) {
        // 미션, 유저, 미션 현황 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found with id: " + missionId));

        Users user = rewardsUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        MyMissionProgress progress = myMissionProgRepository.findByUsersIdAndMissionId(userId, missionId)
                .orElseGet(() -> createNewMissionProgress(user, mission));

        // 미션 진행 및 경험치 증가 계산
        int expGained = updateMissionProgress(progress, mission);

        // 유저 경험치 및 레벨 업데이트
        updateUserExp(user, expGained);
    }

    @Override
    @Transactional
    // 미션 수행 횟수 증가 및 목표 달성 경험치 계산
    public int updateMissionProgress(MyMissionProgress progress, Mission mission) {
        int newMissionCnt = progress.getMissionCnt() + 1;
        int baseExp = mission.getExpPerMission();
        int goalExp = mission.getExpPerGoal();
        int goalCnt = mission.getGoalCnt();

        int totalExpGained = baseExp;
        if (newMissionCnt >= goalCnt) {
            totalExpGained += goalExp;
        }

        progress.setMissionCnt(newMissionCnt);
        myMissionProgRepository.save(progress);
        return totalExpGained;
    }

    @Override
    @Transactional
    // 유저 경험치 증가 및 레벨업 처리
    public void updateUserExp(Users user, int expGained) {
        int newExp = user.getExp() + expGained;
        int newLevel = updateLevel(user.getLevel(), newExp);

        user.setExp(newExp);
        user.setLevel(newLevel);
        rewardsUserRepository.save(user);
    }

    @Override
    @Transactional
    // 레벨업 로직 (경험치 기준 초과 시 연속 레벨업 가능)
    public int updateLevel(int currentLevel, int exp) {
        int level = currentLevel;
        while (exp >= getLevelUpThreshold(level)) {
            exp -= getLevelUpThreshold(level);
            level++;
        }
        return level;
    }

    @Override
    // 레벨업 경험치 기준 계산 (선형 증가)
    public int getLevelUpThreshold(int level) {
        return 50 + (level - 1) * 20;
    }

    // 새로운 미션 진행 데이터 생성
    private MyMissionProgress createNewMissionProgress(Users user, Mission mission) {
        MyMissionProgress progress = MyMissionProgress.builder()
                .users(user)
                .mission(mission)
                .missionCnt(0)
                .weekStartDate(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))) // 이번 주 시작일 설정
                .build();

        return myMissionProgRepository.save(progress);
    }
}