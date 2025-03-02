package com.bovo.Bovo.modules.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoBookMetaDto {
    private int totalCount;
    private int pageableCount;
    private boolean isEnd;
}
