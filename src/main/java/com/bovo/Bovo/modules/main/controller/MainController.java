package com.bovo.Bovo.modules.main.controller;

import com.bovo.Bovo.modules.main.dto.response.partial.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.TotalMainDto;
import com.bovo.Bovo.modules.main.dto.response.partial.UserInfoDto;
import com.bovo.Bovo.modules.main.service.MainService;
import com.bovo.Bovo.modules.my_page.service.MyPageService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final MyPageService myPageService;

    @GetMapping("/main")
    public ResponseEntity<TotalMainDto> loadMain(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();

        UserInfoDto userInfoDto = mainService.getUserInfoByUserId(userId);
        int totalBookNum = myPageService.countCompletedBooksByUserId(userId);
        RecentBookInfoDto recentBookInfoDto = mainService.getRecentBookInfoByUserId(userId);
        Map<String, String> bookList = mainService.getBookListByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new TotalMainDto(200, "메인 페이지 로드 완료",
                        userInfoDto.getProfile_picture(), userInfoDto.getNickname(), userInfoDto.getLevel(), totalBookNum,
                        recentBookInfoDto, bookList));
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new TotalMainDto(200, "메인 페이지 로드 완료",
//                        userInfoDto.getProfile_picture(), userInfoDto.getNickname(), userInfoDto.getLevel(), 30,
//                        recentBookInfoDto, bookList));
    }
}
