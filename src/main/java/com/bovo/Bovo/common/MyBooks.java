package com.bovo.Bovo.common;

import com.bovo.Bovo.modules.archive_detail.domain.ReadingStatus;
import com.bovo.Bovo.modules.archive_detail.domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "my_books")
public class MyBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ManyToOne(fetch = FetchType.LAZY) // User와 N:1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

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

    @Column(precision = 5, scale = 2)
    private BigDecimal bookScore = BigDecimal.ZERO; // 기본값 0

    @Column(nullable = false)
    private Integer bookTotalPages = 0;

    @Column(nullable = false)
    private Integer bookCurrentPages = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReadingStatus readingStatus;

    @Column(nullable = false)
    private LocalDate recentlyCorrectedDate;

    @PrePersist
    public void prePersist() {
        if (bookScore == null) {
            bookScore = BigDecimal.ZERO;
        }
    }
}
