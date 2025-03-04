package com.bovo.Bovo.common;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 적용
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users users;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chat_id", nullable = false)
//    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_leader", nullable = false)
    private LeaderStatus isLeader; // 방장 여부 ENUM

    @Column(name = "field", length = 255)
    private String field; // 비밀방 답변

    // ENUM 타입 정의
    public enum LeaderStatus {
        YES, NO
    }
}
