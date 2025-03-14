package com.bovo.Bovo.modules.archive_detail.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoOrderDTO {

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("memo_order")
    private List<Integer> memoOrder;
}