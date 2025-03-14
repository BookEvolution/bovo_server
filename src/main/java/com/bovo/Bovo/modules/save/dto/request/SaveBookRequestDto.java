package com.bovo.Bovo.modules.save.dto.request;

import com.bovo.Bovo.common.ReadingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookRequestDto {
    private String isbn;
    private String book_name;
    private String book_author;
    private String book_cover;
    private String publication_date;
    private String reading_start_date;
    private String reading_end_date;
    private String book_score;
    private String book_total_pages;
    private String book_current_pages;
    private String is_complete_reading; // JSON에서 "ing", "end", "wish" 값이 들어옴
    private String recently_correction_date;

    // ✅ "ing", "end", "wish" 값을 ReadingStatus Enum으로 변환
    public ReadingStatus getReadingStatusEnum() {
        return switch (is_complete_reading.toLowerCase()) {
            case "ing" -> ReadingStatus.READING;
            case "end" -> ReadingStatus.COMPLETED;
            case "wish" -> ReadingStatus.WANT_TO_READ;
            default -> throw new IllegalArgumentException("잘못된 읽기 상태 값: " + is_complete_reading);
        };
    }
}
