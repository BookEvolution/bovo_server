package com.bovo.Bovo.modules.archive_detail.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoUpdateRequestDto{
    @JsonProperty("book_id") // JSON 키와 Java 필드 매핑
    private Integer bookId;

    @JsonProperty("memo_id")
    private Integer memoId;

    @JsonProperty("memo_Q")
    private String memoQ;

    @JsonProperty("memo_A")
    private String memoA;

}
