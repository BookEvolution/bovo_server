package com.bovo.Bovo.modules.save.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingStatus;
import com.bovo.Bovo.modules.save.dto.request.SaveBookRequestDto;
import com.bovo.Bovo.modules.save.dto.response.SaveBookResponseDto;
import com.bovo.Bovo.modules.save.repository.SaveBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveBookService {
    private final SaveBookRepository saveBookRepository;

    @Transactional
    public SaveBookResponseDto saveBook(Integer user_id, SaveBookRequestDto requestDto) {
        // ✅ DTO에서 변환된 ReadingStatus 사용
        ReadingStatus readingStatusEnum = requestDto.getReadingStatusEnum();

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
                readingStatusEnum, // ✅ 변환된 Enum 전달
                requestDto.getRecently_correction_date()
        );

        // ✅ 저장된 도서 정보 반환
        MyBooks savedBook = saveBookRepository.findBookByIsbnAndUserId(requestDto.getIsbn(), user_id);
        return new SaveBookResponseDto(savedBook);
    }
}
