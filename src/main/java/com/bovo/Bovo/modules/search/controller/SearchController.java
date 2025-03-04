package com.bovo.Bovo.modules.search.controller;

import com.bovo.Bovo.modules.search.dto.request.BookSearchRequestDto;
import com.bovo.Bovo.modules.search.dto.response.BookSearchResponseDto;
import com.bovo.Bovo.modules.search.service.KakaoBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/search")
public class SearchController {
    private final KakaoBookService kakaoBookService;

    public SearchController(KakaoBookService kakaoBookService) {
        this.kakaoBookService = kakaoBookService;
    }

    @GetMapping("/books")
    public ResponseEntity<?> searchBooks(@RequestParam(required = true) String title) {

        //검색어가 없으면 /main으로 리디렉트
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/main")
                    .build();
        }

        //검색 요청 DTO 생성
        BookSearchRequestDto requestDto = new BookSearchRequestDto();
        requestDto.setQuery(title);  // title 값을 query로 설정

        BookSearchResponseDto responseDto = kakaoBookService.searchBooks(requestDto);

        if (responseDto.getBooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }
}

