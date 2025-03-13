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
    private String reading_status; // JSON에서 "ing", "end", "wish" 값이 들어옴
    private String recently_correction_date;

    // ✅ "ing", "end", "wish" 값을 ReadingStatus Enum으로 변환
    public ReadingStatus getReadingStatusEnum() {
        if (reading_status == null) {
            throw new IllegalArgumentException("읽기 상태 값이 필요합니다.");
        }
        switch (reading_status.toLowerCase()) {
            case "ing":
                return ReadingStatus.READING;
            case "end":
                return ReadingStatus.COMPLETED;
            case "wish":
                return ReadingStatus.WANT_TO_READ;
            default:
                throw new IllegalArgumentException("잘못된 읽기 상태: " + reading_status);
        }
    }
}
