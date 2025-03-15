package com.bovo.Bovo.modules.calendar.repository;

import com.bovo.Bovo.modules.calendar.dto.response.partial.CalendarEventDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CalendarRepository {
    List<CalendarEventDto> loadCalendarEvent(Integer userId, LocalDate startDate, LocalDate endDate);
}
