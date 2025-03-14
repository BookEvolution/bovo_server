package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.MedalType;
import com.bovo.Bovo.common.MissionType;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.modules.rewards.repository.MedalRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedalServiceImpl implements MedalService {

    private final MyMissionProgRepository myMissionProgRepository;
    private final MedalRepository medalRepository;

    /**
    * 매주 자동 훈장 업데이트
    */
    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 자정(00:00) 실행
    @Transactional
    @Override
    public void assignWeeklyMedals() {
        // 자동 업데이트를 위한 시간 설정
        LocalDate lastWeekStartDate = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDateTime lastWeekEndTime = lastWeekStartDate.plusDays(6).atTime(23, 59, 59);

        // 모든 user의 지난 주 미션 수행 현황 조회
        List<MyMissionProgress> allProgress = myMissionProgRepository.findAllByWeekStartDate(lastWeekStartDate);

        // user별 미션 수행 현황을 그룹화
        Map<Integer, List<MyMissionProgress>> myProgressMap = allProgress.stream()
                .collect(Collectors.groupingBy(progress -> progress.getUsers().getId()));

        // user별 훈장 지급
        for (Map.Entry<Integer, List<MyMissionProgress>> entry : myProgressMap.entrySet()) {
            Integer userId = entry.getKey();
            List<MyMissionProgress> progressList = entry.getValue();

            // 훈장 지급 미션(COMMUNITY, READING_NOTES)의 수행 횟수 합산
            int totalMissionCount = progressList.stream()
                    .filter(progress -> progress.getMission().getMissionType() == MissionType.COMMUNITY ||
                            progress.getMission().getMissionType() == MissionType.READING_NOTES)
                    .mapToInt(MyMissionProgress::getMissionCnt)
                    .sum();

            // 훈장 지급 기준
            MedalType assignedMedal;
            if (totalMissionCount >= 100) {
                assignedMedal = MedalType.GC; // 최고 등급
            } else if (totalMissionCount >= 80) {
                assignedMedal = MedalType.SC;
            } else if (totalMissionCount >= 60) {
                assignedMedal = MedalType.BC;
            } else if (totalMissionCount >= 40) {
                assignedMedal = MedalType.GT;
            } else if (totalMissionCount >= 20) {
                assignedMedal = MedalType.ST;
            } else if (totalMissionCount >= 10) {
                assignedMedal = MedalType.BT;
            } else if (totalMissionCount >= 7) {
                assignedMedal = MedalType.GM;
            } else if (totalMissionCount >= 4) {
                assignedMedal = MedalType.SM;
            } else if (totalMissionCount >= 2) {
                assignedMedal = MedalType.BM;
            } else {
                assignedMedal = MedalType.NONE;
            }

            // 훈장 업데이트
            medalRepository.updateMedalByUserId(userId, assignedMedal, lastWeekStartDate, lastWeekEndTime);
        }
    }
}