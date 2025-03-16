package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MissionType;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class ExpIncServiceImpl implements ExpIncService {

    private final RewardsUserRepository rewardsUserRepository;
    private final MissionRepository missionRepository;
    private final MyMissionProgRepository myMissionProgRepository;
    private final MedalService medalService;

    @Override
    @Transactional
    public void updateExp(Integer userId, Integer missionId) {
        // 미션, 유저, 미션 현황 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found with id: " + missionId));

        Users user = rewardsUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        MyMissionProgress progress = myMissionProgRepository.findByUsersIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission progress not found for user: " + userId));

        // 미션 카운트 증가 및 기본 경험치 지급
        updateMissionProgress(progress, mission);

        // 유저 경험치 및 레벨 업데이트
        updateUserExp(user, mission.getExpPerMission());

        // 출석 체크 추가 (오늘 날짜 기준)
        checkAttendance(userId);

        // 메달 지급
        medalService.assignWeeklyMedals();
    }

    @Override
    @Transactional
    public void completeQuest(Integer userId, Integer missionId) {
        // 미션, 유저, 진행 데이터 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found with id: " + missionId));

        Users user = rewardsUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        MyMissionProgress progress = myMissionProgRepository.findByUsersIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission progress not found for user: " + userId));

        // 목표 경험치 지급 여부 먼저 체크
        if (progress.isGoalExpGiven()) {
            return; // 이미 지급된 경우 추가 처리 없이 종료
        }

        // 목표 경험치 지급
        updateUserExp(user, mission.getExpPerGoal());
        progress.setGoalExpGiven(true);

        myMissionProgRepository.save(progress);
    }

    @Override
    @Transactional
    // 내 미션 현황 업데이트
    public void updateMissionProgress(MyMissionProgress progress, Mission mission) {
        // 미션 카운트 증가
        int newMissionCnt = progress.getMissionCnt() + 1;
        progress.setMissionCnt(newMissionCnt);

        // 목표 카운트 도달 여부 확인 (중복 저장 방지)
        if (newMissionCnt >= mission.getGoalCnt() && !progress.isCompleted()) {
            progress.setCompleted(true);
        }

        myMissionProgRepository.save(progress);
    }

    @Override
    @Transactional
    // 유저 경험치 증가 및 레벨업 처리
    public void updateUserExp(Users user, int expGained) {
        int newExp = user.getExp() + expGained;

        user.setExp(newExp);
        updateLevel(user);

        rewardsUserRepository.save(user);
    }

    @Override
    @Transactional
    public void updateLevel(Users user) {
        int currentExp = user.getExp();
        int currentLevel = user.getLevel();

        while (currentExp >= getLevelUpThreshold(currentLevel)) {
            currentExp -= getLevelUpThreshold(currentLevel);
            currentLevel++;
        }

        user.setExp(currentExp);
        user.setLevel(currentLevel);
    }

    @Override
    // 레벨업 경험치 기준 계산 (선형 증가)
    public int getLevelUpThreshold(int level) {
        return 100;
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

    // 출석 체크
    private void checkAttendance(Integer userId) {

        Users user = rewardsUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));


        Mission mission = missionRepository.findByMissionType(MissionType.ATTENDANCE)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found for type: " + MissionType.ATTENDANCE));


        MyMissionProgress attendanceProgress = myMissionProgRepository.findByUsersIdAndMissionId(userId, 1)
                .orElseThrow(() -> new IllegalArgumentException("Mission progress not found for mission: " + 1));

        // 오늘 이미 수행한 미션이면 중복 처리 방지
        if (attendanceProgress.getCompleteAt() != null) {
            if (attendanceProgress.getCompleteAt().toLocalDate().isEqual(LocalDate.now())) {
                return;
            }
        }

        int newMissionCnt = attendanceProgress.getMissionCnt() + 1;
        updateUserExp(user, mission.getExpPerMission());

        attendanceProgress.setMissionCnt(newMissionCnt);
        attendanceProgress.setCompleteAt(LocalDateTime.now());

        myMissionProgRepository.save(attendanceProgress);
    }
}