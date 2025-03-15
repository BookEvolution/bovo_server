package com.bovo.Bovo.modules.calendar.repository;

import com.bovo.Bovo.modules.calendar.dto.response.partial.CalendarEventDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@Transactional
public class CalendarRepositoryImpl implements CalendarRepository{
    private final EntityManager em;

    public CalendarRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<CalendarEventDto> loadCalendarEvent(Integer userId, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("SELECT new com.bovo.Bovo.modules.calendar.dto.response.partial.CalendarEventDto(rn.myBooks.bookName, rn.recentlyCorrectionDate) FROM ReadingNotes rn WHERE rn.users.id = :userId AND rn.recentlyCorrectionDate BETWEEN :startDate AND :endDate",
                        CalendarEventDto.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
