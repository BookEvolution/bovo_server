package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;

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
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MedalType medalType;

    @Column(nullable = true)
    private LocalDate medalDate;
}