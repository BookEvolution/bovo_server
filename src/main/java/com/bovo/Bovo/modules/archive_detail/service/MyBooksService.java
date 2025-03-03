package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.modules.archive_detail.domain.MyBooks;
import com.bovo.Bovo.modules.archive_detail.domain.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.domain.ReadingStatus;
import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.repository.MyBooksRepository;
import com.bovo.Bovo.modules.archive_detail.repository.ReadingNotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MyBooksService {

    private final ReadingNotesRepository readingNotesRepository;
    private final MyBooksRepository myBooksRepository;

    //책 id, 유저 id 이용해서 책 정보 가져오기
    public MyBooks myBookInfo(Integer bookId, Integer userId) {
        //책 가져오기
        MyBooks book = myBooksRepository.findOne(bookId);

        //유저 아이디와 검증 로직



        return book;
    }

    @Transactional
    public void updateMyBook(BookUpdateRequestDto requestDto) {
        //책 가져오기
        MyBooks book = myBooksRepository.findOne(requestDto.getBookId());

        //dto정보로 업데이트 하기
        setIsCompleteReading(book, requestDto.getStatus());
        book.setReadingStartDate(requestDto.getStartDate());
        book.setReadingEndDate(requestDto.getEndDate());
        setBookScore(book, requestDto);

        //저장하기

    }

    private static void setBookScore(MyBooks book, BookUpdateRequestDto requestDto) {
        BigDecimal score = new BigDecimal(requestDto.getStar());
        BigDecimal two = new BigDecimal(2);
        BigDecimal divide = score.divide(two, 2, RoundingMode.HALF_UP);

        book.setBookScore(divide);
    }

    private static void setIsCompleteReading(MyBooks book, String getStatus) {
        if (Objects.equals(getStatus, "ing")) {
            book.setIsCompleteReading(ReadingStatus.READING);
        } else if (Objects.equals(getStatus, "end")) {
            book.setIsCompleteReading(ReadingStatus.COMPLETED);
        } else if (Objects.equals(getStatus, "wish")) {
            book.setIsCompleteReading(ReadingStatus.WANT_TO_READ);
        } else{
            log.info("ReadingStatus 매칭 안됨");
        }
    }

    @Transactional
    public void deleteMyBook(Integer bookId) {
        //책 정보 삭제하기

    }




}
