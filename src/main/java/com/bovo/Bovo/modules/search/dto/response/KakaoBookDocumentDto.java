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
public class KakaoBookDocumentDto {
    private String title;
    private List<String> authors;
    private String publisher;
    private String thumbnail;
    private String datetime;
}
