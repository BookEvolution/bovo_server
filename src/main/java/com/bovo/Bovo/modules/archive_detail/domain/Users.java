package com.bovo.Bovo.modules.archive_detail.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    private Integer id;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyBooks> myBooks = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingNotes> readingNotes = new ArrayList<>();

//    @Column(nullable = false, length = 255)
//    private String pass;

//    @Column(nullable = false, length = 30)
//    private String nickname;
//
//    @Column(nullable = false, length = 100)
//    private String profilePicture;
//
//    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 1") // 기본값 1
//    private Integer level;
//
//    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0") // 기본값 0
//    private Integer exp;
//
//    @Column(nullable = false)
//    private LocalDate joinDate;
}
