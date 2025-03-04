package com.bovo.Bovo.common;

import com.bovo.Bovo.modules.archive_detail.domain.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "calendar_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private MyBooks book;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(length = 30, nullable = false)
    private String eventName;
}
