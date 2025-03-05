package com.bovo.Bovo.modules.archive.controller;

import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.archive.dto.Response.MyBooksResponse;
import com.bovo.Bovo.modules.archive.service.MyBooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping
    public ResponseEntity<MyBooksResponse> getMyBooks(@AuthenticationPrincipal Users users) {
        Long userId = users.getId();
        MyBooksResponse response = myBooksService.getMyBooks(userId);
        return ResponseEntity.ok(response);
    }
}
