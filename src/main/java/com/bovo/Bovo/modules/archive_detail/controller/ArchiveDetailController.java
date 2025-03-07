package com.bovo.Bovo.modules.archive_detail.controller;


import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoCreateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.*;
import com.bovo.Bovo.modules.archive_detail.service.ArchiveMyBooksService;
import com.bovo.Bovo.modules.archive_detail.service.ReadingNotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive")
@RequiredArgsConstructor
public class ArchiveDetailController {

    private final ArchiveMyBooksService myBooksService;
    private final ReadingNotesService readingNotesService;
    // 임시 테스트
//    @GetMapping("/{book_id}")
//    public String getBookPage(@PathVariable("book_id") int bookId) {
//        System.out.println(bookId);
//        return "asd";
//    }


    // 기록 페이지 보기
    @GetMapping("/{book_id}")
    public BookDetailResponseDto getBookPage(@PathVariable("book_id") Integer bookId) {
        //유저 아이디 로직
        int userId = 1;

        //책 정보, 메모정보 가져오기
        MyBooks book = myBooksService.myBookInfo(bookId, userId);
        List<ReadingNotes> readingNotes = readingNotesService.memosInfoByBook(bookId, userId);

        return new BookDetailResponseDto(myBooksService.mapBookDTO(book), readingNotesService.mapMemoDTO(readingNotes));
    }


    // 책 정보 수정
    @PutMapping("/{book_id}/update")
    public BookUpdateResponseDto updateBook(@PathVariable("book_id") Integer bookId, @RequestBody BookUpdateRequestDto requestDto) {
        myBooksService.updateMyBook(requestDto);
        return new BookUpdateResponseDto("수정 완료");
    }

    // 책 정보 삭제
    @DeleteMapping("/{book_id}/delete")
    public BookDeleteResponseDto deleteBook(@PathVariable("book_id") Integer bookId) {
        int userId = 1;
        myBooksService.deleteMyBook(bookId, userId);
        return new BookDeleteResponseDto("삭제 완료");
    }


    // 일반 독서 기록 추가
    @PostMapping("/{book_id}/memo")
    public MemoCreateResponseDto addMemo(@PathVariable("book_id") Integer bookId, @RequestBody MemoCreateRequestDto requestDto) {
        int userId = 1;
        readingNotesService.save(bookId, userId, requestDto);
        return new MemoCreateResponseDto("기록 완료");
    }


    // 기록 상세 보기
    @GetMapping("/{book_id}/memo")
    public MemoDTO getMemoDetail(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId) {
        int userId = 1;
        return readingNotesService.viewMemo(bookId, userId, memoId);
    }

    // 기록 수정
    @PutMapping("/{book_id}/memo")
    public MemoUpdateResponseDto updateMemo(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId, @RequestBody MemoUpdateRequestDto requestDto) {
        int userId = 1;
        readingNotesService.updateMemo(memoId,bookId,userId,requestDto);

        return new MemoUpdateResponseDto("수정 완료");
    }

    // 기록 삭제
    @DeleteMapping("/{book_id}/memo")
    public MemoDeleteResponseDto deleteMemo(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId) {
        int userId = 1;
        readingNotesService.deleteMemo(memoId, bookId, userId);

        return new MemoDeleteResponseDto("메모 삭제 완료");
    }

    // 기록 모아 보기
    @GetMapping("/{book_id}/memos")
    public List<MemoDTO> getAllMemos(@PathVariable("book_id") Integer bookId) {
        int userId = 1;
        List<ReadingNotes> readingNotes = readingNotesService.memosInfoByBook(bookId, userId);

        return readingNotesService.mapMemoDTO(readingNotes);
    }

    // 기록 순서 변경
    @PutMapping("/{book_id}/memos")
    public MemoReorderResponseDto reorderMemos(@PathVariable("book_id") Integer bookId) {
        return new MemoReorderResponseDto();
    }
}
