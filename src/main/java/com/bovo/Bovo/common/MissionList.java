package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission_list")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "missionList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MissionComplete> missionComplete = new ArrayList<>();

    @Column(nullable = false)
    private String missionName;

    @Column(nullable = false)
    private int missionExp;
}
