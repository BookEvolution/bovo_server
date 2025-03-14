package com.bovo.Bovo.modules.chatrooms.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomRequestDTO {

    @JsonProperty("book_info")
    @NotNull
    private BookInfoDTO bookInfo;

    @JsonProperty("chat_info")
    @NotNull
    private ChatInfoDTO chatInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookInfoDTO {
        @JsonProperty("book_name")
        //@NotBlank
        private String bookName;

        @JsonProperty("book_cover")
        private String bookCover;

        @JsonProperty("book_author")
        //@NotBlank
        private String bookAuthor;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatInfoDTO {
        @JsonProperty("chat_name")
        @NotBlank
        private String chatName;

        @JsonProperty("chat_detail")
        private String chatDetail;

        @JsonProperty("challenge_start_date")
        @NotNull
        private LocalDate challengeStartDate;

        @JsonProperty("challenge_end_date")
        @NotNull
        private LocalDate challengeEndDate;

        @JsonProperty("is_secret")
        private boolean isSecret;

        @JsonProperty("secret_question")
        private String secretQuestion;

        @JsonProperty("secret_answer")
        private String secretAnswer;

        @JsonProperty("max_recruiting")
        private Integer maxRecruiting;

    }
}