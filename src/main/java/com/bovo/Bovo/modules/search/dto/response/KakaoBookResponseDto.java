package com.bovo.Bovo.modules.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoBookResponseDto {
    private List<KakaoBookDocumentDto> documents;
    private KakaoBookMetaDto meta;
}
