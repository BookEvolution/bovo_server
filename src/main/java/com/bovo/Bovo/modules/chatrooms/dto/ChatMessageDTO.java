package com.bovo.Bovo.modules.chatrooms.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private Long id;
    private String message;
    private LocalDateTime timestamp;
    private String messageType;
    private String email;  // 🔹 Users 엔티티의 ID만 포함
    private Integer chatRoomId; // 🔹 ChatRoom 엔티티의 ID만 포함
    private String nickname; // 🔹 추가적으로 사용자 닉네임 포함
}