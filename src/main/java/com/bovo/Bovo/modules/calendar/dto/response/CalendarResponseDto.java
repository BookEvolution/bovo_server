package com.bovo.Bovo.modules.calendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CalendarResponseDto {
    private int status;
    private String message;
    private Map<String, List<String>> book_calendar_list;
}
