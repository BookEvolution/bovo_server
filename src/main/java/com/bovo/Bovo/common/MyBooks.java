package com.bovo.Bovo.common;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "my_books")
public class MyBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // User와 N:1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @OneToMany(mappedBy = "myBooks", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReadingNotes> readingNotesList = new ArrayList<>(); // *********

    @OneToMany(mappedBy = "myBooks", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalendarEvent> calendarEvent = new ArrayList<>();

    @Column(nullable = false, length = 50)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String bookName;

    @Column(nullable = false, length = 100)
    private String bookAuthor;

    @Column(length = 500)
    @Nullable
    private String bookCover;

    @Column
    @Nullable
    private LocalDate publicationDate;

    @Column(nullable = false)
    private LocalDate readingStartDate;

    @Column(nullable = false)
    private LocalDate readingEndDate;

    @Column(precision = 5, scale = 2)
    @Nullable
    private BigDecimal bookScore = BigDecimal.ZERO; // 기본값 0

    @Column
    @Nullable
    private Integer bookTotalPages = 0;

    @Column
    @Nullable
    private Integer bookCurrentPages = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReadingStatus readingStatus; //TODO ERD에는 is_complete_reading으로 나와있음.

    @Column(nullable = false)
    private LocalDate recentlyCorrectionDate;


}
