package com.bovo.Bovo.modules.search.controller;

import com.bovo.Bovo.modules.search.dto.request.BookSearchRequestDto;
import com.bovo.Bovo.modules.search.dto.response.BookSearchResponseDto;
import com.bovo.Bovo.modules.search.service.KakaoBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final KakaoBookService kakaoBookService;

    @Autowired
    public SearchController(KakaoBookService kakaoBookService) {
        this.kakaoBookService = kakaoBookService;
    }

@GetMapping
    public ResponseEntity<BookSearchResponseDto> searchBooks(@ModelAttribute BookSearchRequestDto requestDto) {
        if (requestDto.getQuery() == null || requestDto.getQuery().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new BookSearchResponseDto("error", 0, 0, 0, Collections.emptyList()));
        }

        BookSearchResponseDto responseDto = kakaoBookService.searchBooks(requestDto);

        if (responseDto.getBooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }

        return ResponseEntity.ok(responseDto);
    }
}

