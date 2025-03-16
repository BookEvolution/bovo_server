package com.bovo.Bovo.modules.chatrooms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemoDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd")
    private LocalDate memo_date;
    private String memo_Q;
    private String memo_A;


}
