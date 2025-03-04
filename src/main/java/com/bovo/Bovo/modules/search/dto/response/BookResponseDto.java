package com.bovo.Bovo.modules.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private String title; // 도서 제목
    private String author; // 저자
    private String publisher; // 출판사
    private String coverImage; // 책 표지 URL
    private String publishedDate; // 출판일
}

