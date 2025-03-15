package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.rewards.repository.MissionRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import com.bovo.Bovo.modules.rewards.repository.RewardsUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class ExpIncServiceImpl implements ExpIncService {

    private final RewardsUserRepository rewardsUserRepository;
    private final MissionRepository missionRepository;
    private final MyMissionProgRepository myMissionProgRepository;

    // 퀘스트 달성 경험치 지급
    @Override
    @Transactional
    public void updateGoalExp(Integer userId, Integer missionId) {

        Users user = rewardsUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with id: " + missionId));

        // 기본 및 목표 달성 경험치 지급 및 레벨업
        updateUserExpAndLevel(user, mission.getExpPerMission());
        updateUserExpAndLevel(user, mission.getExpPerGoal());

        // 미션 진행 현황 업데이트
        updateMissionProgress(user, mission);

        // 변경 사항 저장
        rewardsUserRepository.save(user);
    }

    // 경험치 지급 및 레벨업
    private void updateUserExpAndLevel(Users user, int earnedExp) {
        int currentExp = user.getExp();
        int updatedExp = currentExp + earnedExp;
        int currentLevel = user.getLevel();
        int levelUpThreshold = getLevelUpThreshold(currentLevel);

        while (updatedExp >= levelUpThreshold) {
            updatedExp -= levelUpThreshold;
            currentLevel++;
            levelUpThreshold = getLevelUpThreshold(currentLevel);
        }

        user.setExp(updatedExp);
        user.setLevel(currentLevel);
    }

    // 미션 진행 현황 업데이트
    private void updateMissionProgress(Users user, Mission mission) {
        LocalDate thisWeekStartDate = getThisWeekStartDate();
        MyMissionProgress progress = myMissionProgRepository
                .findByUserIdAndMissionIdAndWeekStartDate(user.getId(), mission.getId(), thisWeekStartDate)
                .orElseGet(() -> MyMissionProgress.builder()
                        .users(user)
                        .mission(mission)
                        .missionCnt(0)
                        .weekStartDate(thisWeekStartDate)
                        .isCompleted(false)
                        .isGoalExpGiven(false)
                        .missionAt(LocalDateTime.now())
                        .completeAt(LocalDateTime.now())
                        .build());

        progress.setMissionCnt(progress.getMissionCnt() + 1);
        if (progress.getMissionCnt() >= mission.getGoalCnt()) {
            progress.setCompleted(true);
        }
        progress.setMissionAt(LocalDateTime.now());
        myMissionProgRepository.save(progress);
    }

    // 이번주 시작 일자 계산
    private LocalDate getThisWeekStartDate() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    // 레벨업 경험치 설정
    private int getLevelUpThreshold(int level) {
        return 50 + (level - 1) * 20;
    }
}

