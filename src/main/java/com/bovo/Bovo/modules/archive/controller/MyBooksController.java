package com.bovo.Bovo.modules.archive.controller;

import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.archive.dto.Response.MyBooksResponse;
import com.bovo.Bovo.modules.archive.service.MyBooksService;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archive")
public class MyBooksController {

    private final MyBooksService myBooksService;

    public MyBooksController(MyBooksService myBooksService) {
        this.myBooksService = myBooksService;
    }

    // userId를 받아서 내 서재 조회
    // *** 경로변수로 userId 받는 방법 (임시 테스트용) ***
    @GetMapping("/{user_id}")
    public ResponseEntity<MyBooksResponse> getMyBooks(@PathVariable("user_id") Integer userId) {
        MyBooksResponse response = myBooksService.getMyBooks(userId);
        return ResponseEntity.ok(response);
    }

    // *** @AuthenticationPrincipal 사용하여 userId 받는 방법 ***
//    @GetMapping
//    public ResponseEntity<MyBooksResponse> getMyBooks(@AuthenticationPrincipal Users users) {
//        Integer userId = users.getId();
//        MyBooksResponse response = myBooksService.getMyBooks(userId);
//        return ResponseEntity.ok(response);
//    }
}
