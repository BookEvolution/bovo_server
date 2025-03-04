package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.modules.archive_detail.domain.MyBooks;
import com.bovo.Bovo.modules.archive_detail.domain.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoCreateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.MemoDTO;
import com.bovo.Bovo.modules.archive_detail.repository.ReadingNotesRepository;
import com.bovo.Bovo.modules.archive_detail.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadingNotesService {

    private final ReadingNotesRepository readingNotesRepository;
    private final MyBooksService myBooksService;
    private final UsersRepository usersRepository;

    //메모 저장하기
    @Transactional
    public void save(Integer bookId, Integer userId, MemoCreateRequestDto requestDto){
        ReadingNotes memo = ReadingNotes.builder()
                .book(myBooksService.myBookInfo(bookId, userId))
                .users(usersRepository.findOne(userId))
                .memoQuestion(requestDto.getMemoA())
                .memoAnswer(requestDto.getMemoA())
                .recentlyCorrectionDate(myBooksService.stringToLocalDate(requestDto.getMemoDate()))
                .build();
        readingNotesRepository.save(memo);
    }
    //책에서 메모리스트 가져오기
    public List<ReadingNotes> memosInfoByBook(Integer bookId, Integer userId) {
        MyBooks book = myBooksService.myBookInfo(bookId, userId);

        List<ReadingNotes> readingNotes = book.getReadingNotes();

        return readingNotes;
    }

    //메모 하나 보가
    public MemoDTO viewMemo(Integer bookId, Integer userId, Integer memoId ) {
        //메모 찾기
        ReadingNotes memo = readingNotesRepository.memoFindOne(memoId);

        //책 검증
        boolean validation = memo.getBook().equals(myBooksService.myBookInfo(bookId, userId));

        //디티오 매핑
        if(validation) {
            return MemoDTO.builder()
                    .memoDate(myBooksService.localDateToString(memo.getRecentlyCorrectionDate()))
                    .memoA(memo.getMemoQuestion())
                    .memoQ(memo.getMemoAnswer())
                    .build();
        }
        else{
            return new MemoDTO();
        }
    }

    //메모리스트 디티오 변환
    public List<MemoDTO> mapMemoDTO(List<ReadingNotes> readingNotes) {
        List<MemoDTO> memos = new ArrayList<>();
        for (ReadingNotes readingNote : readingNotes) {
            MemoDTO memo = new MemoDTO(readingNote.getId()
                    , myBooksService.localDateToString(readingNote.getRecentlyCorrectionDate())
                    , readingNote.getMemoAnswer()
                    , readingNote.getMemoQuestion());
            memos.add(memo);
        }
        return memos;
    }

    //메모 수정
    @Transactional
    public void updateMemo(Integer memoId, Integer BookId, MemoUpdateRequestDto requestDto) {
        //책에 있는 메모인지 검증 로직

        //메모 수정

    }

    //메모 삭제
    @Transactional
    public void deleteMemo(Integer memoId, Integer bookId) {
        //책에 있는 메모인지 검증 로직


        //책 삭제
        readingNotesRepository.delete(readingNotesRepository.memoFindOne(memoId));
    }


}
