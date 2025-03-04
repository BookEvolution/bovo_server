package com.bovo.Bovo.modules.archive_detail.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailResponseDto {

    private BookDTO book;
    private List<MemoDTO> memos;

}
