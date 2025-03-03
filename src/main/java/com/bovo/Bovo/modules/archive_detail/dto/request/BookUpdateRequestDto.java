package com.bovo.Bovo.modules.archive_detail.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Getter @Setter
public class BookUpdateRequestDto {

    @JsonProperty("book_id")
    private Integer bookId;

    private String status;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private int star;
}
