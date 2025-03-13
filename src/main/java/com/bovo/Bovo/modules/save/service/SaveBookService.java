package com.bovo.Bovo.modules.save.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.modules.save.dto.request.SaveBookRequestDto;
import com.bovo.Bovo.modules.save.dto.response.SaveBookResponseDto;
import com.bovo.Bovo.modules.save.repository.SaveBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveBookService {
    private final SaveBookRepository saveBookRepository;

    //@Transactional
    public SaveBookResponseDto saveBook(Integer user_id, SaveBookRequestDto requestDto) {
        try {
            // ✅ user_id 값이 null인지 확인
            System.out.println("[DEBUG] saveBook() 호출됨: user_id = " + user_id);

            if (user_id == null) {
                return SaveBookResponseDto.errorResponse(HttpStatus.UNAUTHORIZED, "사용자 인증이 필요합니다.");
            }


            // ✅ 중복 체크
            if (saveBookRepository.existsByIsbnAndUserId(requestDto.getIsbn(), user_id)) {
                MyBooks existingBook = saveBookRepository.findBookByIsbnAndUserId(requestDto.getIsbn(), user_id);
                return SaveBookResponseDto.duplicateBookResponse(existingBook);
            }

            // ✅ 도서 저장 시 예외 발생 여부 확인
            System.out.println("[DEBUG] 도서 저장 실행: ISBN = " + requestDto.getIsbn());

            saveBookRepository.saveBook(
                    user_id,
                    requestDto.getIsbn(),
                    requestDto.getBook_name(),
                    requestDto.getBook_author(),
                    requestDto.getBook_cover(),
                    requestDto.getPublication_date(),
                    requestDto.getReading_start_date(),
                    requestDto.getReading_end_date(),
                    requestDto.getBook_score(),
                    requestDto.getBook_total_pages(),
                    requestDto.getBook_current_pages(),
                    requestDto.getReadingStatusEnum(), // ✅ `is_complete_reading` 값을 변환한 Enum 사용
                    requestDto.getRecently_correction_date()
            );

            System.out.println("[DEBUG] 도서 저장 성공: ISBN = " + requestDto.getIsbn());

            // ✅ 저장된 도서 정보 반환
            MyBooks savedBook = saveBookRepository.findBookByIsbnAndUserId(requestDto.getIsbn(), user_id);
            return new SaveBookResponseDto(savedBook);

        } catch (Exception e) {
            System.out.println("[ERROR] 도서 저장 중 예외 발생: " + e.getMessage());
            return SaveBookResponseDto.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
