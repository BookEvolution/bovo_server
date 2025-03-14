package com.bovo.Bovo.common;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reading_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadingNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 설정
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // 책과 N:1 관계
    @JoinColumn(name = "book_id", nullable = false)
    private MyBooks myBooks;

    @ManyToOne(fetch = FetchType.LAZY) // 유저와 N:1 관계
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;


    @Column(length = 255)
    private String memoQuestion;

    @Column(columnDefinition = "TEXT")
    private String memoAnswer; // 답변 (내용)

    @Column(nullable = false)
    private LocalDate recentlyCorrectionDate; // 최근 수정 날짜 (최초 작성 날짜도 동일)

    @Column(name = "order_index")
    private Integer order;
}
