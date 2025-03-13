package com.bovo.Bovo.modules.save.dto.response;

import com.bovo.Bovo.common.MyBooks;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor // ✅ 기본 생성자 추가 (JSON 직렬화 가능)
public class SaveBookResponseDto {
    private String status;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    // ✅ 성공 응답 생성자
    public SaveBookResponseDto(MyBooks book) {
        this.status = "success";
        this.message = "도서가 내 서재에 성공적으로 추가되었습니다.";

        this.data.put("title", book.getBookName());
        this.data.put("reading_start_date", book.getReadingStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        this.data.put("recently_correction_date", book.getRecentlyCorrectionDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    // ✅ 에러 응답 생성자 추가
    public SaveBookResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
