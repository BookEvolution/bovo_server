package com.bovo.Bovo.modules.rewards.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 제외하고 응답
public class MyMissionProgDto {
    private Integer missionId; // missionId에 대응되는 미션 유형의 필드값이 전달됨 (나머지 필드는 null)

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