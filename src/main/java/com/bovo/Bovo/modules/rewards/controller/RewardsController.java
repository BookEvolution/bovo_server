package com.bovo.Bovo.modules.rewards.controller;

import com.bovo.Bovo.modules.rewards.dto.request.ExpIncRequestDto;
import com.bovo.Bovo.modules.rewards.dto.response.ExpIncResponseDto;
import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgResponseDto;
import com.bovo.Bovo.modules.rewards.service.ExpIncService;
import com.bovo.Bovo.modules.rewards.service.RewardsService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rewards")
public class RewardsController {

    // userId로 내 미션 현황 목록 조회
    private final RewardsService rewardsService;
    private final ExpIncService expIncService;

    @GetMapping
    public ResponseEntity<MyMissionProgResponseDto> getMyMissionProg(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        MyMissionProgResponseDto response = rewardsService.getMyMissionProgByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // 퀘스트 달성시 기본 및 추가 경험치 지급
    @PutMapping("/exp-increase")
    public ResponseEntity<ExpIncResponseDto> updateGoalExp(
            @AuthenticationPrincipal AuthenticatedUserId user,
            @RequestBody ExpIncRequestDto request) {

        Integer userId = user.getUserId();
        Integer missionId = request.getMissionId();

        expIncService.updateGoalExp(userId, missionId);
        ExpIncResponseDto response = new ExpIncResponseDto(200);

        return ResponseEntity.ok(response);
    }
}