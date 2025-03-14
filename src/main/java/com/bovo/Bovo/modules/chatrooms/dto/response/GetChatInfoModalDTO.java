package com.bovo.Bovo.modules.chatrooms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChatInfoModalDTO {

    @NotNull
    private BookInfoDTO bookInfo;

    @NotNull
    private ChatInfoDTO chatInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookInfoDTO {
        @JsonProperty("book_name")
        @NotBlank
        private String bookName;


        @JsonProperty("book_cover")
        @NotBlank
        private String bookCover;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatInfoDTO {

        @JsonProperty("chat_name")
        @NotBlank
        private String chatName;

        @JsonProperty("chat_detail")
        @NotBlank
        private String chatDetail;

        @JsonProperty("challenge_start_date")
        @NotNull
        private LocalDate challengeStartDate;

        @JsonProperty("challenge_end_date")
        @NotNull
        private LocalDate challengeEndDate;

        @JsonProperty("limit_people")
        @NotNull
        private Integer limitPeople;

        @JsonProperty("current_people")
        @NotNull
        private Integer currentPeople;

        @JsonProperty("is_secret")
        @NotNull
        private Boolean isSecret;


        @JsonProperty("secret_question")
        private String secretQuestion;
        @JsonProperty("secret_answer")
        private String secretAnswer;
    }
}