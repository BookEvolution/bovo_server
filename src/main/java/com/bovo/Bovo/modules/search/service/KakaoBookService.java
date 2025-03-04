package com.bovo.Bovo.modules.search.service;

import com.bovo.Bovo.modules.search.dto.request.BookSearchRequestDto;
import com.bovo.Bovo.modules.search.dto.response.BookResponseDto;
import com.bovo.Bovo.modules.search.dto.response.BookSearchResponseDto;
import com.bovo.Bovo.modules.search.dto.response.KakaoBookResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class KakaoBookService {

    private final String KAKAO_API_URL = "https://dapi.kakao.com/v3/search/book";
    private final String API_KEY = "KakaoAK {YOUR_REST_API_KEY}";

    private final RestTemplate restTemplate;

    @Autowired
    public KakaoBookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BookSearchResponseDto searchBooks(BookSearchRequestDto requestDto) {
        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                .queryParam("query", requestDto.getQuery())
                .queryParam("page", requestDto.getPage())
                .queryParam("size", requestDto.getSize())
                .queryParam("sort", requestDto.getSort())
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoBookResponseDto> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, KakaoBookResponseDto.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return mapToBookSearchResponseDto(response.getBody(), requestDto.getPage());
        }

        return new BookSearchResponseDto("error", 0, requestDto.getPage(), 0, Collections.emptyList());
    }

    private BookSearchResponseDto mapToBookSearchResponseDto(KakaoBookResponseDto kakaoResponse, int currentPage) {
        List<BookResponseDto> books = kakaoResponse.getDocuments().stream().map(doc -> new BookResponseDto(
                doc.getTitle(),
                doc.getAuthors().isEmpty() ? "저자 미상" : String.join(", ", doc.getAuthors()),
                doc.getPublisher(),
                doc.getThumbnail(),
                doc.getDatetime()
        )).toList();

        int totalResults = kakaoResponse.getMeta().getTotalCount();
        int totalPages = (int) Math.ceil((double) totalResults / 10);

        return new BookSearchResponseDto("success", totalResults, currentPage, totalPages, books);
    }
}

