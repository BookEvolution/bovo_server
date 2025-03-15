package com.bovo.Bovo.modules.calendar.dto.response.partial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventDto {
    private String bookName;
    private LocalDate recentlyCorrectionDate;
}
