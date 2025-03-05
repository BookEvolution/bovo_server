package com.bovo.Bovo.modules.archive_detail.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoDTO {
    private Integer memoId;
    private String memoDate;
    private String memoQ;
    private String memoA;
}
