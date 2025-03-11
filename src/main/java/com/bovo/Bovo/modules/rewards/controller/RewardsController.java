package com.bovo.Bovo.modules.rewards.controller;

import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgResponseDto;
import com.bovo.Bovo.modules.rewards.service.RewardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }


    @GetMapping
    public ResponseEntity<MyMissionProgResponseDto> getMyMissionProg(@AuthenticationPrincipal Users users) {
        Integer userId = users.getId();
        MyMissionProgResponseDto response = rewardsService.getMyMissionProg(userId);
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/exp-increase")

}
