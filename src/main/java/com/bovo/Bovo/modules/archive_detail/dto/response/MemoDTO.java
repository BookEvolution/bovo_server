package com.bovo.Bovo.modules.archive_detail.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoDTO {
    @JsonProperty("memo_id")
    private Integer memoId;

    @JsonProperty("memo_date")
    private String memoDate;

    @JsonProperty("memo_Q")
    private String memoQ;
    @JsonProperty("memo_A")
    private String memoA;
}
