package com.bovo.Bovo.common;

import lombok.Getter;

@Getter
public enum MissionName {
    ATTENDANCE("출석"),
    COMMUNITY("커뮤니티 참여"),
    BOOK_REG("책 등록"),
    READING_NOTES("독서 기록");

    private final String description;

    MissionName(String description) {
        this.description = description;
    }
}