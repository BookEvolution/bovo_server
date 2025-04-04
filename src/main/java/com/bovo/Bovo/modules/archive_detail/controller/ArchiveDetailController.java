package com.bovo.Bovo.modules.archive_detail.controller;


import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoCreateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoOrderDTO;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.*;
import com.bovo.Bovo.modules.archive_detail.service.ArchiveMyBooksService;
import com.bovo.Bovo.modules.archive_detail.service.ReadingNotesService;
import com.bovo.Bovo.modules.rewards.service.ExpIncService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive")
@RequiredArgsConstructor
public class ArchiveDetailController {

    private final ArchiveMyBooksService myBooksService;
    private final ReadingNotesService readingNotesService;
    private final ExpIncService expIncService;

    // 기록 페이지 보기
    @GetMapping("/{book_id}")
    public BookDetailResponseDto getBookPage(@PathVariable("book_id") Integer bookId, @AuthenticationPrincipal AuthenticatedUserId user) {
        //유저 아이디 로직
        int userId = user.getUserId();

        //책 정보, 메모정보 가져오기
        MyBooks book = myBooksService.myBookInfo(bookId, userId);
        List<ReadingNotes> readingNotes = readingNotesService.memosInfoByBook(bookId, userId);

        return new BookDetailResponseDto(myBooksService.mapBookDTO(book), readingNotesService.mapMemoDTO(readingNotes));
    }


    // 책 정보 수정
    @PutMapping("/{book_id}/update")
    public BookUpdateResponseDto updateBook(@PathVariable("book_id") Integer bookId, @RequestBody BookUpdateRequestDto requestDto, @AuthenticationPrincipal AuthenticatedUserId user) {
        myBooksService.updateMyBook(requestDto);
        return new BookUpdateResponseDto("수정 완료");
    }

    // 책 정보 삭제
    @DeleteMapping("/{book_id}/delete")
    public BookDeleteResponseDto deleteBook(@PathVariable("book_id") Integer bookId, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();
        myBooksService.deleteMyBook(bookId, userId);
        return new BookDeleteResponseDto("삭제 완료");
    }


    // 일반 독서 기록 추가
    @PostMapping("/{book_id}/memo")
    public MemoCreateResponseDto addMemo(@PathVariable("book_id") Integer bookId, @RequestBody MemoCreateRequestDto requestDto, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();
        readingNotesService.save(bookId, userId, requestDto);
        expIncService.updateExp(userId, 4);
        return new MemoCreateResponseDto("기록 완료");
    }


    // 기록 상세 보기
    @GetMapping("/{book_id}/memo")
    public MemoDTO getMemoDetail(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();
        return readingNotesService.viewMemo(bookId, userId, memoId);
    }

    // 기록 수정
    @PutMapping("/{book_id}/memo")
    public MemoUpdateResponseDto updateMemo(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId, @RequestBody MemoUpdateRequestDto requestDto, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();
        readingNotesService.updateMemo(memoId,bookId,userId,requestDto);

        return new MemoUpdateResponseDto("수정 완료");
    }

    // 기록 삭제
    @DeleteMapping("/{book_id}/memo")
    public MemoDeleteResponseDto deleteMemo(@PathVariable("book_id") Integer bookId, @RequestParam("memoId") Integer memoId, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();
        readingNotesService.deleteMemo(memoId, bookId, userId);

        return new MemoDeleteResponseDto("메모 삭제 완료");
    }

    // 기록 모아 보기
    @GetMapping("/{book_id}/memos")
    public MemoListResponseDto getAllMemos(@PathVariable("book_id") Integer bookId, @AuthenticationPrincipal AuthenticatedUserId user) {
        int userId = user.getUserId();

        List<ReadingNotes> readingNotes = readingNotesService.memosInfoByBook(bookId, userId);
        MyBooks myBook = myBooksService.myBookInfo(bookId, userId);
        MemoBookInfo memoBookInfo = new MemoBookInfo(myBook.getBookName(), myBook.getBookAuthor(), myBook.getReadingStartDate(), myBook.getReadingEndDate());
        return new MemoListResponseDto(memoBookInfo,readingNotesService.mapMemoDTO(readingNotes));
    }

    // 기록 순서 변경
    @PutMapping("/{book_id}/memos")
    public MemoReorderResponseDto reorderMemos(@PathVariable("book_id") Integer bookId, @AuthenticationPrincipal AuthenticatedUserId user,@RequestBody MemoOrderDTO requestDTO) {
        System.out.println(requestDTO.getBookId());
        readingNotesService.orderUpdateMemo(requestDTO);
        return new MemoReorderResponseDto("변경 완료");
    }
}
