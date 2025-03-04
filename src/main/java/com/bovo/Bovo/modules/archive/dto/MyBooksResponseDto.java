package com.bovo.Bovo.modules.archive.dto;

import com.bovo.Bovo.modules.archive.domain.MyBooks;
import com.bovo.Bovo.modules.archive.domain.ReadingStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyBooksResponseDto {

    private Integer id;
    private ReadingStatus isCompleteReading;
    private String bookCover;
    private LocalDate readingStartDate;
    private LocalDate readingEndDate;
    private BigDecimal bookScore;
    private int bookCurrentPages;

    // Entity에서 필요한 필드값을 DTO에 설정하여 반환
    public static MyBooksResponseDto of(MyBooks myBooks) {
        return MyBooksResponseDto.builder()
                .id(myBooks.getId())
                .isCompleteReading(myBooks.getIsCompleteReading())
                .bookCover(myBooks.getBookCover())
                .readingStartDate(myBooks.getReadingStartDate())
                .readingEndDate(myBooks.getReadingEndDate())
                .bookScore(myBooks.getBookScore())
                .bookCurrentPages(myBooks.getBookCurrentPages())
                .build();
    }

}