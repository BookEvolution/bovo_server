package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.modules.archive_detail.domain.MyBooks;
import com.bovo.Bovo.modules.archive_detail.domain.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.repository.MyBooksRepository;
import com.bovo.Bovo.modules.archive_detail.repository.ReadingNotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
    public void updateMyBook(Integer bookId, BookUpdateRequestDto requestDto) {
        //책 가져오기

        //dto정보로 업데이트 하기

        //저장하기

    }

    @Transactional
    public void deleteMyBook(Integer bookId) {
        //책 정보 삭제하기

    }




}
