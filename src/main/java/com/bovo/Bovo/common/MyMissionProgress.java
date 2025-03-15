package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_mission_progress")
@Setter
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
    private int missionCnt = 0;

    @Column
    private LocalDateTime missionAt;

    @Column(nullable = false)
    private boolean isCompleted = false;

    @Column(nullable = false)
    private boolean isGoalExpGiven = false;

    @Column
    private LocalDateTime completeAt;

    @Column
    private LocalDate weekStartDate;
}

