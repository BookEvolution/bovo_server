package com.bovo.Bovo.common;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 적용
    private Integer id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participation = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();


    @Column(name = "chat_name", length = 30, nullable = false)
    private String chatName;

    @Column(name = "max_recruiting", nullable = false)
    private Integer maxRecruiting;

    @Column(name = "challenge_start_date", nullable = false)
    private LocalDate challengeStartDate;

    @Column(name = "challenge_end_date", nullable = false)
    private LocalDate challengeEndDate;

    @Column(name = "chat_info", length = 255)
    private String chatInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_secret", nullable = false)
    private SecretType isSecret;

    @Column(name = "secret_question", length = 30)
    private String secretQuestion;

    @Column(name = "book_name", length = 100, nullable = false)
    private String bookName;

    @Column(name = "book_cover", length = 100)
    private String bookCover;

    @Column(name = "book_author", length = 100, nullable = false)
    private String bookAuthor;

    private String admin;

    // ENUM 타입 정의
    public enum SecretType {
        YES, NO
    }
}
