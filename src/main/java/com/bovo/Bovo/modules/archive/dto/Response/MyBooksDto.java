package com.bovo.Bovo.modules.archive.dto.Response;

import com.bovo.Bovo.common.ReadingStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyBooksDto {
    private Integer bookId;
    private ReadingStatus readingStatus;
    private String bookCover;
    private String bookName;
    private String bookAuthor;
    private String readingStartDate; // (YY. MM. DD) 형식으로 변환된 시작 날짜
    private BigDecimal bookScore;
}

