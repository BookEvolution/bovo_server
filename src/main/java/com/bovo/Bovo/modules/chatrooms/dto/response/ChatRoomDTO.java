package com.bovo.Bovo.modules.chatrooms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("chatroom_name")
    private String chatroomName;

    @JsonProperty("chatroom_ds")
    private String chatroomDs;

    @JsonProperty("book_info")
    private BookInfo bookInfo;

    @JsonProperty("duration")
    private Duration duration;

    @JsonProperty("group_info")
    private GroupInfo groupInfo;

    @JsonProperty("admin")
    private boolean admin;



    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInfo {
        @JsonProperty("book_img")
        private String bookImg;

        @JsonProperty("book_name")
        private String bookName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Duration {
        @JsonProperty("start_date")
        private LocalDate startDate;

        @JsonProperty("end_date")
        private LocalDate endDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupInfo {
        @JsonProperty("limit_people")
        private Integer limitPeople;

        @JsonProperty("current_people")
        private Integer currentPeople;
    }
}