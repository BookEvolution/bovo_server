package com.bovo.Bovo.modules.chatrooms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyChatRoomResponseDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("chatroom_name")
    private String chatroomName;

    @JsonProperty("book_info")
    private BookInfo bookInfo;

    @JsonProperty("last_msg_info")
    private LastMessageInfo lastMsgInfo;

    @JsonProperty("no_reading_count")
    private Integer noReadingCount;
    @Getter
    @Setter
    @Builder
    public static class BookInfo {
        @JsonProperty("book_img")
        private String bookImg;
    }

    @Getter
    @Setter
    @Builder
    public static class LastMessageInfo {
        @JsonProperty("last_message")
        private String lastMessage;

        @JsonProperty("last_message_date")
        private LocalDateTime lastMessageDate;
    }
}

