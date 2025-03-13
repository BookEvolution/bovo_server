package com.bovo.Bovo.modules.save.controller;

import com.bovo.Bovo.modules.save.dto.request.SaveBookRequestDto;
import com.bovo.Bovo.modules.save.dto.response.SaveBookResponseDto;
import com.bovo.Bovo.modules.save.service.SaveBookService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/save")
@RequiredArgsConstructor
public class SaveController {
    private final SaveBookService saveBookService;

    @PostMapping
public ResponseEntity<?> saveBook(@AuthenticationPrincipal AuthenticatedUserId user,
                                  @RequestBody SaveBookRequestDto requestDto) {
    Integer user_id = user.getUserId();

    try {
        SaveBookResponseDto savedBook = saveBookService.saveBook(user_id, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SaveBookResponseDto("error", e.getMessage())); // ✅ 에러 응답 반환
    }
}

}
