package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission_complete")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private MissionList missionList;

    @Column(nullable = false)
    private LocalDateTime completedTime;
}

