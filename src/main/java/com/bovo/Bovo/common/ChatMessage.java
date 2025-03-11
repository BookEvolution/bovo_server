package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // User와 N:1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    private String message; // 메시지 내용
    private LocalDateTime timestamp; // 메시지 전송 시간

    @Enumerated(EnumType.STRING)
    private MessageType type; // ENTER, CHAT, LEAVE 타입 구분

    public enum MessageType {
        ENTER, CHAT, LEAVE
    }
}