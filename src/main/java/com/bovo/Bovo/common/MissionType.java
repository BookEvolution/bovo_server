package com.bovo.Bovo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum MissionType {
    ATTENDANCE("출석", 5, 10, 7),
    COMMUNITY("커뮤니티 참여", 10, 20, 7),
    BOOK_REG("책 등록", 5, 10, 7),
    READING_NOTES("독서 기록", 10, 20, 7);

    private final String description;
    private final int expPerMission; // 미션 1회 수행 시 지급 경험치
    private final int expPerGoal;    // 주간 퀘스트(7회 달성) 시 추가 지급 경험치
    private final int goalCnt;
}