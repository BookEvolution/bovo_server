package com.bovo.Bovo.modules.rewards.controller;

import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgResponseDto;
import com.bovo.Bovo.modules.rewards.service.RewardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    // userId로 내 미션 현황 목록 조회
    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping
    public ResponseEntity<MyMissionProgResponseDto> getMyMissionProg(@AuthenticationPrincipal Users users) {
        Integer userId = users.getId();
        MyMissionProgResponseDto response = rewardsService.getMyMissionProgByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // 퀘스트 달성 추가 경험치 지급
//    @PutMapping("/exp-increase")

}