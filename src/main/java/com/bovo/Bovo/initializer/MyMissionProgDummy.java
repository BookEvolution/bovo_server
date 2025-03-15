package com.bovo.Bovo.initializer;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.rewards.repository.MissionRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import com.bovo.Bovo.modules.rewards.repository.RewardsUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyMissionProgDummy implements CommandLineRunner {

    private final MyMissionProgRepository myMissionProgressRepository;
    private final RewardsUserRepository rewardsUserRepository;
    private final MissionRepository missionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MyMissionProgDummy(MyMissionProgRepository myMissionProgressRepository,
                                      RewardsUserRepository rewardsUserRepository,
                                      MissionRepository missionRepository) {
        this.myMissionProgressRepository = myMissionProgressRepository;
        this.rewardsUserRepository = rewardsUserRepository;
        this.missionRepository = missionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 기존의 my_mission_progress 테이블 데이터를 삭제
        myMissionProgressRepository.deleteAll();
        System.out.println("Deleted existing MyMissionProgress data.");

        // 사용자 1명 가져오기
        Users user = rewardsUserRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No users found in the database!"));

        // 미션 목록 가져오기 (4개)
        List<Mission> missions = missionRepository.findAll();
        if (missions.size() < 4) {
            throw new RuntimeException("Not enough missions found in the database!");
        }

        List<MyMissionProgress> progressToSave = new ArrayList<>();

        // 각 미션에 대한 진행 정보 생성
        for (Mission mission : missions) {
            MyMissionProgress progress = MyMissionProgress.builder()
                    .users(user)
                    .mission(mission)
                    .missionCnt((int) (Math.random() * 7)) // 0~6 랜덤 미션 수행 횟수
                    .missionAt(LocalDateTime.now().minusDays((int) (Math.random() * 7))) // 최근 수행 날짜
                    .isCompleted(false) // 초기값은 완료되지 않음
                    .isGoalExpGiven(false) // 목표 경험치 지급 안 됨
                    .completeAt(LocalDateTime.of(2024, 3, 14, 10, 30)) // 완료 날짜 및 시간
                    .weekStartDate(LocalDate.of(2024, 3, 11)) // 해당 주의 월요일
                    .build();

            progressToSave.add(progress);
        }

        // MyMissionProgressRepository를 사용해 한 번에 저장
        myMissionProgressRepository.saveAll(progressToSave);
        System.out.println("Inserted " + progressToSave.size() + " mission progress records.");
    }
}

