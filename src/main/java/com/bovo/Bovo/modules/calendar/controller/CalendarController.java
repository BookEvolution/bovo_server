package com.bovo.Bovo.modules.calendar.controller;

import com.bovo.Bovo.modules.calendar.dto.response.CalendarResponseDto;
import com.bovo.Bovo.modules.calendar.service.CalendarService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/my-page/calendar")
    public ResponseEntity<CalendarResponseDto> loadCalendarEvent(@AuthenticationPrincipal AuthenticatedUserId user, @RequestParam("year") int year, @RequestParam("month") int month) {
        Integer userId = user.getUserId();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);

        Map<String, List<String>> calendarBookList = calendarService.loadCalendarBookList(userId ,startDate, endDate);

        System.out.println("userId: "+userId);
        System.out.println("calendarBookList: " + calendarBookList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CalendarResponseDto(200, "캘린더 로드 완료", calendarBookList));
    }
}
