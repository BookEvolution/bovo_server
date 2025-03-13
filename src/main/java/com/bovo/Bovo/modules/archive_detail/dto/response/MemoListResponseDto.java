package com.bovo.Bovo.modules.archive_detail.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoListResponseDto {


    private MemoBookInfo book;

    private List<MemoDTO> memos;

}
