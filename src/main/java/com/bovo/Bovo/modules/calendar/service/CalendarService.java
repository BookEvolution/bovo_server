package com.bovo.Bovo.modules.calendar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CalendarService {
    Map<String, List<String>> loadCalendarBookList(Integer userId, LocalDate startDate, LocalDate endDate);
}
