package com.bovo.Bovo.modules.rewards.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyMissionProgDto {
    private Integer missionId; // 미션 종류에 따라 값이 채워짐

    // 출석 미션 현황
    private Integer loginCnt;
    private Boolean isLoginCompleted;

    // 커뮤니티 참여 미션 현황
    private Integer communityCnt;
    private Boolean isCommunityCompleted;

    // 책 등록 미션 현황
    private Integer bookRegCnt;
    private Boolean isBookRegCompleted;

    // 독서 기록 미션 현황
    private Integer noteCnt;
    private Boolean isNoteCompleted;
}