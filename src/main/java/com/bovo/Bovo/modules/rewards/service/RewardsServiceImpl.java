package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.Medal;
import com.bovo.Bovo.common.MedalType;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgDto;
import com.bovo.Bovo.modules.rewards.dto.response.MyMissionProgResponseDto;
import com.bovo.Bovo.modules.rewards.repository.MedalRepository;
import com.bovo.Bovo.modules.rewards.repository.MyMissionProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardsServiceImpl implements RewardsService {

    private final MyMissionProgRepository myMissionProgRepository;
    private final MedalRepository medalRepository;

    @Transactional
    @Override
    public MyMissionProgResponseDto getMyMissionProgByUserId(Integer userId) {

        // userId로 내 미션 현황 목록 조회
        List<MyMissionProgress> myMissionProgList = myMissionProgRepository.findAllByUsers_Id(userId);

        // MyMissionProgress 엔티티에서 필요한 필드값을 추출하여 DTO에 전달
        // - MissionId를 통해 미션 유형에 맞게 전달됨
        List<MyMissionProgDto> missions = myMissionProgList.stream()
                .map(progress -> {
                    MyMissionProgDto.MyMissionProgDtoBuilder builder = MyMissionProgDto.builder()
                            .missionId(progress.getMission().getId());

                    switch (progress.getMission().getMissionType()) {
                        case ATTENDANCE:
                            builder.loginCnt(progress.getMissionCnt())
                                    .isLoginCompleted(progress.isCompleted());
                            break;
                        case COMMUNITY:
                            builder.communityCnt(progress.getMissionCnt())
                                    .isCommunityCompleted(progress.isCompleted());
                            break;
                        case BOOK_REG:
                            builder.bookRegCnt(progress.getMissionCnt())
                                    .isBookRegCompleted(progress.isCompleted());
                            break;
                        case READING_NOTES:
                            builder.noteCnt(progress.getMissionCnt())
                                    .isNoteCompleted(progress.isCompleted());
                            break;
                        default:
                            // case에 지정되지 않은 미션 유형인 경우
                            throw new IllegalArgumentException("Unknown mission type: " + progress.getMission().getMissionType());
                    }
                    return builder.build();
                }).collect(Collectors.toList());

        MedalType medalType = medalRepository.findByUsers_Id(userId)
                .map(Medal::getMedalType)
                .orElse(MedalType.NONE);

        return MyMissionProgResponseDto.builder()
                .myMissionProgDtoList(missions)
                .medalType(medalType.name())
                .build();
    }
}
