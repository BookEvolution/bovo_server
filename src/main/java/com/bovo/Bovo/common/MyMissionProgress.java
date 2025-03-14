package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_mission_progress")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMissionProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(nullable = false)
    private int missionCnt;

    @Column(nullable = false)
    private LocalDateTime missionAt;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private boolean isGoalExpGiven;

    @Column(nullable = false)
    private LocalDateTime completeAt;

    @Column(nullable = false)
    private LocalDate weekStartDate;
}

