package com.bovo.Bovo.modules.search.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequestDto {
    private String query;  // 검색어 (필수)
    private int page = 1;  // 페이지 번호 (기본값: 1)
    private int size = 10; // 한 페이지당 결과 수 (기본값: 10)
    private String sort = "accuracy"; // 정렬 기준 (기본값: 정확도순)
}

