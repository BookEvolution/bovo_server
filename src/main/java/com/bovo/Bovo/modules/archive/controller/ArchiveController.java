package com.bovo.Bovo.modules.archive.controller;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MissionType;
import com.bovo.Bovo.modules.archive.dto.ArchiveResponseDto;
import com.bovo.Bovo.modules.archive.service.ArchiveService;
import com.bovo.Bovo.modules.rewards.repository.MissionRepository;
import com.bovo.Bovo.modules.rewards.service.ExpIncService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    // userId로 내 서재 조회
    @GetMapping
    public ResponseEntity<ArchiveResponseDto> getMyBooks(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        ArchiveResponseDto response = archiveService.getMyBooks(userId);
        return ResponseEntity.ok(response);
    }
}