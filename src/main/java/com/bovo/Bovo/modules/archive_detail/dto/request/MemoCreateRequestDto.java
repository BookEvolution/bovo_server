package com.bovo.Bovo.modules.archive_detail.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoCreateRequestDto {
    @JsonProperty("book_id") // JSON의 키와 Java 필드 매핑
    private Integer bookId;

    @JsonProperty("memo_Q")
    private String memoQ;

    @JsonProperty("memo_A")
    private String memoA;

    @JsonProperty("memo_date")
    private String memoDate; // "YY.MM.DD" 형식의 문자열
}
