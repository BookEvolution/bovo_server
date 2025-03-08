package com.bovo.Bovo.modules.my_page.controller;

import com.bovo.Bovo.modules.my_page.dto.request.NewProfileUpdateDto;
import com.bovo.Bovo.modules.my_page.dto.response.ProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.ProfileUpdateFormDto;
import com.bovo.Bovo.modules.my_page.dto.response.TotalProfileDto;
import com.bovo.Bovo.modules.user.dto.request.EmailDto;
import com.bovo.Bovo.modules.user.dto.response.*;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import com.bovo.Bovo.modules.my_page.service.MyPageService;
import com.bovo.Bovo.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {
    private final UserService userService;
    private final MyPageService myPageService;

    private void expiredCookie(HttpServletResponse responseCookie) {
        ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("LAX")
                .path("/")
                .maxAge(0)
                .build();
        responseCookie.setHeader("Set-Cookie", expiredCookie.toString());
    }

    @PostMapping("/logout")
    public ResponseEntity<defResponseDto> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        System.out.println("로그아웃 실행"+refreshToken);

        SecurityContextHolder.clearContext();

        if (refreshToken != null) {
            Integer userId = userService.extractUserIdFromRefreshToken(refreshToken);
            if (userId != null) {
                boolean delete = userService.deleteRefreshToken(userId);
            }
        }

        expiredCookie(response);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new defResponseDto(200, "로그아웃 성공"));
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<DeleteUserDto> userdelete(@RequestBody EmailDto emailDto) {
        System.out.println("회원 탈퇴 로직 시작");

        // 이메일로 조회해서 삭제하기
        if (!userService.existEmail(emailDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DeleteUserDto(404, "존재하지 않은 사용자 이메일",null));
        }

        Integer deleteUserId = userService.deleteUserByEmail(emailDto.getEmail());
        System.out.println("회원 탈퇴 성공" + deleteUserId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new DeleteUserDto(200, "회원 탈퇴 성공", deleteUserId));
    }

    @GetMapping("")
    public ResponseEntity<TotalProfileDto> totalProfile(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        PartialProfileDto partialProfileDto = myPageService.findPartialMyPageByUserId(userId);
        int total_book_num = myPageService.countCompletedBooksByUserId(userId);
        String medal = myPageService.findLastMedalByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new TotalProfileDto(200, "프로필 전체 정보",
                        partialProfileDto.getProfile_picture(), partialProfileDto.getNickname(), partialProfileDto.getLevel(),
                        partialProfileDto.getExp(), total_book_num, medal));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDetailDto> profileDetail(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        PartialProfileDetailDto partialProfileDetailDto = myPageService.findPartialProfileDetailByUserId(userId);
        String medal = myPageService.findLastMedalByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProfileDetailDto(200, "자세한 프로필 정보",
                        partialProfileDetailDto.getProfile_picture(), partialProfileDetailDto.getNickname(), partialProfileDetailDto.getEmail(),
                        partialProfileDetailDto.getLevel(), medal));
    }

    @GetMapping("/profile/update")
    public ResponseEntity<ProfileUpdateFormDto> showProfileUpdateForm(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        PartialProfileUpdateDto partialProfileUpdateDto= myPageService.findPartialProfileUpdateByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProfileUpdateFormDto(200, "프로필 수정 폼 불러오기", partialProfileUpdateDto.getProfile_pictures(), partialProfileUpdateDto.getNickname()));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<defResponseDto> updateProfileUpdateForm(@RequestBody NewProfileUpdateDto newProfileUpdateDto, @AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new defResponseDto(200, "프로필 수정 완료"));
    }
}
