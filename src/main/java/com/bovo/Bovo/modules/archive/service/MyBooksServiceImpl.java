package com.bovo.Bovo.modules.archive.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.modules.archive.dto.Response.MyBooksDto;
import com.bovo.Bovo.modules.archive.dto.Response.MyBooksResponse;
import com.bovo.Bovo.modules.archive.repository.MyBooksRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyBooksServiceImpl implements MyBooksService {

    private final MyBooksRepository myBooksRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy. MM. dd");

    public MyBooksServiceImpl(MyBooksRepository myBooksRepository) {
        this.myBooksRepository = myBooksRepository;
    }

    @Override
    public MyBooksResponse getMyBooks(Long userId) {
        // 사용자 ID에 해당하는 내 서재의 책 목록을 조회
        List<MyBooks> myBooksList = myBooksRepository.findByUserId(userId);

        // MyBooks 엔티티에서 필요한 필드만 추출하여 DTO로 변환
        List<MyBooksDto> myBooksDtoList = myBooksList.stream()
                .map(myBooks -> MyBooksDto.builder()
                        .bookId(myBooks.getId())
                        .readingStatus(myBooks.getReadingStatus())
                        .bookCover(myBooks.getBookCover())
                        .bookName(myBooks.getBookName())
                        .bookAuthor(myBooks.getBookAuthor())
                        .readingStartDate(myBooks.getReadingStartDate().format(formatter))
                        .bookScore(myBooks.getBookScore())
                        .build())
                .collect(Collectors.toList());

        return MyBooksResponse.builder()
                .myBooksList(myBooksDtoList)
                .build();
    }
}

