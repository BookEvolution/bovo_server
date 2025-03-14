package com.bovo.Bovo.modules.save.dto.response;

import com.bovo.Bovo.common.MyBooks;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor // ✅ 기본 생성자 추가 (JSON 직렬화 가능)
public class SaveBookResponseDto {
    private String status;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    // ✅ 성공 응답 (도서 추가 성공)
    public SaveBookResponseDto(MyBooks book) {
        this.status = "success";
        this.message = "도서가 내 서재에 성공적으로 추가되었습니다.";

        this.data.put("title", book.getBookName());
        this.data.put("reading_start_date", book.getReadingStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        this.data.put("recently_correction_date", book.getRecentlyCorrectionDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    // ✅ 중복 도서 응답 (200 OK)
    public static SaveBookResponseDto duplicateBookResponse(MyBooks book) {
        SaveBookResponseDto response = new SaveBookResponseDto();
        response.status = "success";
        response.message = "이미 내 서재에 추가된 도서입니다.";

        response.data.put("title", book.getBookName());
        return response;
    }

    // ✅ 오류 응답 (400, 404, 500 등)
    public static SaveBookResponseDto errorResponse(HttpStatus status, String message) {
        SaveBookResponseDto response = new SaveBookResponseDto();
        response.status = "error";
        response.message = (message != null && !message.isEmpty()) ? message : "알 수 없는 오류입니다.";
        return response;
    }
}
