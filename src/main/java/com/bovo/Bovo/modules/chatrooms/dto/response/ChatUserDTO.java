package com.bovo.Bovo.modules.chatrooms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserDTO {
    private String profilePictures;
    private String email;
    private String nickname;
}