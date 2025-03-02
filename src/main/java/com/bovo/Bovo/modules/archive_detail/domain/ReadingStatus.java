package com.bovo.Bovo.modules.archive_detail.domain;

import lombok.Getter;

@Getter
public enum ReadingStatus {
    READING("읽는 중"),
    COMPLETED("다 읽음"),
    WANT_TO_READ("읽고 싶음");

    private final String description;

    ReadingStatus(String description) {
        this.description = description;
    }

}
