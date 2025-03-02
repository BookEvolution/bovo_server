package com.bovo.Bovo.modules.archive_detail.controller;


import com.bovo.Bovo.modules.archive_detail.dto.request.BookUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoCreateResquestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoUpdateResquestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("/archive")
@RequiredArgsConstructor
public class ArchiveDetailController {
//    // 임시 테스트
//    @GetMapping("/{book_id}")
//    public String getBookPage(@PathVariable("book_id") int bookId) {
//        System.out.println(bookId);
//        return "asd";
//    }

    // 기록 페이지 보기
    @GetMapping("/{book_id}")
    public BookDetailResponseDto getBookPage(@PathVariable("book_id") Long bookId) {
        BookDetailResponseDto responseDto = new BookDetailResponseDto();
//        responseDto.setMessage("asd");
        // {"message"}
        return responseDto;
    }


    // 책 정보 수정
    @PutMapping("/{book_id}/update")
    public BookUpdateResponseDto updateBook(@PathVariable("book_id") Long bookId, @RequestBody BookUpdateRequestDto requestDto) {
        return new BookUpdateResponseDto();
    }

    // 책 정보 삭제
    @DeleteMapping("/{book_id}/delete")
    public BookDeleteResponseDto deleteBook(@PathVariable("book_id") Long bookId) {
        return new BookDeleteResponseDto();
    }


    // 일반 독서 기록 추가
    @PostMapping("/{book_id}/memo")
    public MemoCreateResponseDto addMemo(@PathVariable("book_id") Long bookId, @RequestBody MemoCreateResquestDto requestDto) {
        return new MemoCreateResponseDto();
    }


    // 기록 상세 보기
    @GetMapping("/{book_id}/memo")
    public MemoDetailResponseDto getMemoDetail(@PathVariable("book_id") Long bookId, @RequestParam("memoId") Long memoId) {
        return new MemoDetailResponseDto();
    }

    // 기록 수정
    @PutMapping("/{book_id}/memo")
    public MemoUpdateResponseDto updateMemo(@PathVariable("book_id") Long bookId, @RequestParam("memoId") Long memoId, @RequestBody MemoUpdateResquestDto requestDto) {
        return new MemoUpdateResponseDto();
    }

    // 기록 삭제
    @DeleteMapping("/{book_id}/memo")
    public MemoDeleteResponseDto deleteMemo(@PathVariable("book_id") Long bookId, @RequestParam("memoId") Long memoId) {
        return new MemoDeleteResponseDto();
    }

    // 기록 모아 보기
    @GetMapping("/{book_id}/memos")
    public MemoListResponseDto getAllMemos(@PathVariable("book_id") Long bookId) {
        return new MemoListResponseDto();
    }

    // 기록 순서 변경
    @PutMapping("/{book_id}/memos")
    public MemoReorderResponseDto reorderMemos(@PathVariable("book_id") Long bookId) {
        return new MemoReorderResponseDto();
    }
}
