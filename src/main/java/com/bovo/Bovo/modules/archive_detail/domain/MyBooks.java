package com.bovo.Bovo.modules.archive_detail.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "my_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 설정
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // User와 N:1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private Users user_id;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingNotes> readingNotes = new ArrayList<>();

    @Column(nullable = false, length = 50)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String bookName;

    @Column(nullable = false, length = 100)
    private String bookAuthor;

    @Column(length = 100)
    private String bookCover;

    private LocalDate publicationDate;

    @Column(nullable = false)
    private LocalDate readingStartDate;

    @Column(nullable = false)
    private LocalDate readingEndDate;

    @Column(precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0")
    private BigDecimal bookScore;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookTotalPages;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookCurrentPages;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReadingStatus isCompleteReading;

    @Column(nullable = false)
    private LocalDate recentlyCorrectionDate;
}
