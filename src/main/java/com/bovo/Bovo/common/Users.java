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
@Builder
@AllArgsConstructor
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
    private List<MyMissionProgress> myMissionProgress = new ArrayList<>();

    // 카카오 로그인 추가 시 null 허용으로 변경 예정
    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String profile_picture; // 상대 경로

    @Column(nullable = false)
    private int level = 1;

    @Column(nullable = false)
    private int exp = 0;

    @Column(nullable = false)
    private LocalDateTime join_date;

    public static Users createUser(String profile_picture, String nickname, String email) {
        return Users.builder()
                .profile_picture(profile_picture)
                .nickname(nickname)
                .email(email)
                .join_date(LocalDateTime.now())
                .build();
    }
}
