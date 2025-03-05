package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "mission_list")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String missionName;

    @Column(nullable = false)
    private int missionExp;
}
