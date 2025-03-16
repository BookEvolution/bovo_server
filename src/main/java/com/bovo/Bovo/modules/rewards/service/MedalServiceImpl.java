package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.MedalType;
import com.bovo.Bovo.common.MissionType;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.modules.rewards.repository.MedalRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedalServiceImpl implements MedalService {

    private final MyMissionProgRepository myMissionProgRepository;
    private final MedalRepository medalRepository;

    private boolean isProcessing = false; // 중복 실행 방지용 락 변수

    // 자동 업데이트를 위한 시간 설정
    private final LocalDate lastWeekStartDate = LocalDate.now().minusWeeks(1)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    private final LocalDateTime lastWeekStartDateTime = lastWeekStartDate.atStartOfDay();

    // 서버 재시작 시 지난주 데이터가 업데이트되지 않았을 경우 실행
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void initializeMedals() {
        boolean exists = medalRepository.existsByWeekStartDate(lastWeekStartDateTime);
        if (!exists) {
            assignWeeklyMedals();
        }
    }

    // 매주 월요일 00:00 훈장 자동 업데이트
    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void assignWeeklyMedals() {
        synchronized (this) {
            if (isProcessing) {
                return;
            }
            isProcessing = true;
        }

        try {
            processWeeklyMedals();
        } finally {
            isProcessing = false;
        }
    }

    // 훈장 지급 로직
    @Transactional
    public void processWeeklyMedals() {
        List<MyMissionProgress> allProgress = myMissionProgRepository.findAllByWeekStartDate(lastWeekStartDate);

        // user별 미션 수행 횟수 그룹화
        Map<Integer, List<MyMissionProgress>> myProgressMap = allProgress.stream()
                .collect(Collectors.groupingBy(progress -> progress.getUsers().getId()));

        for (Map.Entry<Integer, List<MyMissionProgress>> entry : myProgressMap.entrySet()) {
            Integer userId = entry.getKey();
            List<MyMissionProgress> progressList = entry.getValue();

            // 특정 미션(COMMUNITY, READING_NOTES)의 수행 횟수 합산
            int totalMissionCount = progressList.stream()
                    .filter(progress -> progress.getMission().getMissionType() == MissionType.COMMUNITY ||
                            progress.getMission().getMissionType() == MissionType.READING_NOTES)
                    .mapToInt(MyMissionProgress::getMissionCnt)
                    .sum();

            // 훈장 지급 기준
            MedalType assignedMedal;
            if (totalMissionCount >= 100) {
                assignedMedal = MedalType.GC;
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

            // 갱신 시간 이번주 월요일 자정
            LocalDateTime medalAt = lastWeekStartDateTime.plusWeeks(1);

            // 바로 훈장 갱신 (조회 없이 UPDATE 수행)
            medalRepository.updateMedalByUserId(userId, assignedMedal, lastWeekStartDateTime, medalAt);
        }

        // 2주 전 데이터 삭제 (이번주, 지난주 데이터만 유지)
        LocalDate thresholdDate = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .minusWeeks(2);

        myMissionProgRepository.deleteAllByWeekStartDateBefore(thresholdDate);
    }
}
