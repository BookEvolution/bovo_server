package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingNotes;
import com.bovo.Bovo.common.ReadingStatus;
import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.BookDTO;
import com.bovo.Bovo.modules.archive_detail.repository.ArchiveMyBooksRepository;
import com.bovo.Bovo.modules.archive_detail.repository.ArchiveReadingNotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArchiveMyBooksService {

    private final ArchiveReadingNotesRepository readingNotesRepository;
    private final ArchiveMyBooksRepository myBooksRepository;

    //책 id, 유저 id 이용해서 책 정보 가져오기
    public MyBooks myBookInfo(Integer bookId, Integer userId) {
        //책 가져오기
        MyBooks book = myBooksRepository.findOne(bookId);

        //유저 아이디로 검증


        return book;
    }

    public BookDTO mapBookDTO(MyBooks book) {
        BookDTO bookDTO = BookDTO.builder()
                .title(book.getBookName())
                .cover(book.getBookCover())
                .author(book.getBookAuthor())
                .star(book.getBookScore().multiply(BigDecimal.TWO).intValue())
                .startDate(localDateToString(book.getReadingStartDate()))
                .endDate(localDateToString(book.getReadingEndDate()))
                .status(setBookReadingStatusDto(book))
                .build();


        return bookDTO;
    }

    private static String setBookReadingStatusDto(MyBooks book) {
        if(book.getReadingStatus().equals(ReadingStatus.WANT_TO_READ)){
            return "wish";
        } else if (book.getReadingStatus().equals(ReadingStatus.READING)){
            return "ing";
        } else if (book.getReadingStatus().equals(ReadingStatus.COMPLETED)) {
            return "end";
        }
        return "오류";
    }

    public String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }


    @Transactional
    public void updateMyBook(BookUpdateRequestDto requestDto) {
        //책 가져오기
        MyBooks book = myBooksRepository.findOne(requestDto.getBookId());

        //dto정보로 업데이트 하기
        //setIsCompleteReading(book, requestDto.getStatus());
        book.setReadingStatus(setIsCompleteReading(requestDto.getStatus()));
        book.setReadingStartDate(stringToLocalDate(requestDto.getStartDate()));
        book.setReadingEndDate(stringToLocalDate(requestDto.getEndDate()));
        setBookScore(book, requestDto);
    }

    public LocalDate stringToLocalDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        return LocalDate.parse(date, inputFormatter);
    }



    private static void setBookScore(MyBooks book, BookUpdateRequestDto requestDto) {
        BigDecimal score = new BigDecimal(requestDto.getStar());
        BigDecimal two = new BigDecimal(2);
        BigDecimal divide = score.divide(two, 2, RoundingMode.HALF_UP);

        book.setBookScore(divide);
    }

    private static ReadingStatus setIsCompleteReading(String getStatus) {
        if (Objects.equals(getStatus, "ing")) {
            return ReadingStatus.READING;
        } else if (Objects.equals(getStatus, "end")) {
            return ReadingStatus.COMPLETED;
        } else if (Objects.equals(getStatus, "wish")) {
            return ReadingStatus.WANT_TO_READ;
        } else{
            log.info("ReadingStatus 매칭 안됨");
            return ReadingStatus.READING;
        }
    }

    @Transactional
    public Integer deleteMyBook(Integer bookId, Integer userId) {
        //책 정보 가져오기
        MyBooks book = myBookInfo(bookId, userId);

        //책 삭제
        myBooksRepository.delete(book);


        return bookId;
    }




}
