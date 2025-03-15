package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "medal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users users;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MedalType medalType = MedalType.NONE;

    @Column(nullable = false)
    private LocalDateTime weekStartDate;

    @Column(nullable = false)
    private LocalDateTime medalAt; // 훈장이 갱신되는 시각
}