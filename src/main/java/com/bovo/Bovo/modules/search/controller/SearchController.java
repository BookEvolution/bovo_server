package com.bovo.Bovo.modules.search.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final RestTemplate restTemplate = new RestTemplate();;
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v3/search/book";

    @Value("${kakao.api.key}")
    private String KAKAO_API_KEY ;

    @GetMapping
    public ResponseEntity<?> searchBooks(@RequestParam String query,
                                         @RequestParam(defaultValue = "accuracy") String sort,
                                         @RequestParam(defaultValue = "20") Integer size) {

        System.out.println("query = " + query);


        // 강제 디코딩 인코딩
        String decodedQuery = URLDecoder.decode(query, StandardCharsets.UTF_8);
        String encodedQuery = URLEncoder.encode(decodedQuery, StandardCharsets.UTF_8);

        // Kakao API 호출 URL 생성
        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
            .queryParam("query", decodedQuery)
            .queryParam("sort", sort)
            .queryParam("size", size)
            .toUriString();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", KAKAO_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Kakao API 요청 및 응답
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response.getBody());
    }
}
