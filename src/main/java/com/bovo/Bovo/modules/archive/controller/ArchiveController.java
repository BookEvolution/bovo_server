package com.bovo.Bovo.modules.archive.controller;

import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.archive.dto.ArchiveResponseDto;
import com.bovo.Bovo.modules.archive.service.ArchiveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    // userId로 내 서재 조회
    @GetMapping
    public ResponseEntity<ArchiveResponseDto> getMyBooks(@AuthenticationPrincipal Users users) {
        Integer userId = users.getId();
        ArchiveResponseDto response = archiveService.getMyBooks(userId);
        return ResponseEntity.ok(response);
    }
}