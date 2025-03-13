package com.bovo.Bovo.modules.chatrooms.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        @NotBlank
        private String bookName;

        @NotBlank
        private String bookCover;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatInfoDTO {
        @NotBlank
        private String chatName;

        @NotBlank
        private String chatDetail;

        @NotNull
        private LocalDate challengeStartDate;

        @NotNull
        private LocalDate challengeEndDate;

        @NotNull
        private Integer limitPeople;

        @NotNull
        private Integer currentPeople;

        @NotNull
        private Boolean isSecret;

        private String secretQuestion;
        private String secretAnswer;
    }
}