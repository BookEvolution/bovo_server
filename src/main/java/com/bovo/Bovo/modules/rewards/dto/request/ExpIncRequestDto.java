package com.bovo.Bovo.modules.rewards.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpIncRequestDto {

    @NotNull(message = "user_id는 필수 항목입니다.")
    private Long userId;

    @NotNull(message = "mission_id는 필수 항목입니다.")
    private Long missionId;
}