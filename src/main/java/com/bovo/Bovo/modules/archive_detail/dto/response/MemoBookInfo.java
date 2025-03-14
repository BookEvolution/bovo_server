package com.bovo.Bovo.modules.archive_detail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoBookInfo {

    private String title;
    private String author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd")
    private LocalDate start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd")
    private LocalDate end_date;
}
