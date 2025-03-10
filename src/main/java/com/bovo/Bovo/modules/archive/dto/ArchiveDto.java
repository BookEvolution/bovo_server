package com.bovo.Bovo.modules.archive.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArchiveDto {
    private Integer bookId;
    private String status;
    private String cover;
    private String title;
    private String author;
    private String startDate; // (YY. MM. DD) 형식으로 변환된 시작 날짜
    private int star;
}