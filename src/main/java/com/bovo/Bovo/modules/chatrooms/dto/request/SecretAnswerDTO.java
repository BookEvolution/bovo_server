package com.bovo.Bovo.modules.chatrooms.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretAnswerDTO {
    @JsonProperty("secret_answer")
    private String secretAnswer;
}