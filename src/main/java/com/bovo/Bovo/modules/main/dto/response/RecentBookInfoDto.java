package com.bovo.Bovo.modules.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecentBookInfoDto {
    private String bookName;
    private String bookAuthor;
    private String bookCover;
    private LocalDate recentlyCorrectionDate;
    private BigDecimal bookScore;
}
