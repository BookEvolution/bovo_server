package com.bovo.Bovo.modules.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchResponseDto {
    private String status; // API 상태 (success, error)
    private int totalResults; // 전체 검색 결과 수
    private int currentPage; // 현재 페이지 번호
    private int totalPages; // 총 페이지 수
    private List<BookResponseDto> books; // 검색된 도서 리스트
}

