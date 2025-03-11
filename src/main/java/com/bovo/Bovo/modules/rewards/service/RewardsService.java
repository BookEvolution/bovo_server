package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgResponseDto;

public interface RewardsService {
    // userId로 내 미션 현황 조회
    MyMissionProgResponseDto getMyMissionProg(Integer userId);
}
