package com.bovo.Bovo.modules.save.controller;

import com.bovo.Bovo.modules.rewards.service.ExpIncService;
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
    private final ExpIncService expIncService;

    @PostMapping
    public ResponseEntity<?> saveBook(@AuthenticationPrincipal AuthenticatedUserId user,
                                      @RequestBody SaveBookRequestDto requestDto) {
        // ✅ user_id가 null인지 체크
        if (user == null || user.getUserId() == null) {
            System.out.println("[ERROR] 사용자 인증 정보 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }


        Integer user_id = user.getUserId();
        SaveBookResponseDto responseDto = saveBookService.saveBook(user_id, requestDto);

        // ✅ 상태 코드 설정
        if ("error".equals(responseDto.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } else if ("success".equals(responseDto.getStatus()) && "이미 내 서재에 추가된 도서입니다.".equals(responseDto.getMessage())) {
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        }

        expIncService.updateExp(user.getUserId(), 3);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
