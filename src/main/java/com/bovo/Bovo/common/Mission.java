package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mission_list")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionType missionType;

    @Column(nullable = false)
    private int expPerMission; // 미션 수행 경험치

    @Column(nullable = false)
    private int goalCnt;       // 목표 횟수

    @Column(nullable = false)
    private int expPerGoal;    // 목표 달성 경험치

}
