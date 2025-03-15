package com.bovo.Bovo.modules.calendar.service;

import com.bovo.Bovo.modules.calendar.dto.response.partial.CalendarEventDto;
import com.bovo.Bovo.modules.calendar.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarServiceImpl implements CalendarService{
    private final CalendarRepository calendarRepository;

    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }


    @Override
    public Map<String, List<String>> loadCalendarBookList(Integer userId, LocalDate startDate, LocalDate endDate) {
        List<CalendarEventDto> CalendarEvents = calendarRepository.loadCalendarEvent(userId, startDate, endDate);
        System.out.println("CalendarEvents = " + CalendarEvents);

        Map<String, List<String>> bookCalendar = new LinkedHashMap<>();

        for (CalendarEventDto CalendarEvent : CalendarEvents) {
            String bookName = (String) CalendarEvent.getBookName();
            String recentlyCorrectionDate = CalendarEvent.getRecentlyCorrectionDate().toString();
            bookCalendar.computeIfAbsent(bookName, k -> new ArrayList<>()).add(recentlyCorrectionDate);
        }
        return bookCalendar;
    }
}
