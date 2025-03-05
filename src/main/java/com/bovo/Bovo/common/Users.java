package com.bovo.Bovo.common;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 불필요한 객체 생성 방지
@Entity
@Table(name = "users")
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_Auth> userAuth = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyBooks> myBooks = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participation = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingNotes> readingNotes = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarEvent> calendarEvent = new ArrayList<>();

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Medal medal;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MissionComplete> missionComplete = new ArrayList<>();

    // 카카오 로그인 추가 시 null 허용으로 변경 예정
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profile_picture; // 상대 경로

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private LocalDateTime join_date;

    @Builder
    public Users(String profile_picture, String nickname, String email) {
        this.profile_picture = profile_picture;
        this.nickname = nickname;
        this.email = email;
        this.level = 1;
        this.exp = 0;
        this.join_date = LocalDateTime.now();
    }
}
