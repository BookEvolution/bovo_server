package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingNotes;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoCreateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoOrderDTO;
import com.bovo.Bovo.modules.archive_detail.dto.request.MemoUpdateRequestDto;
import com.bovo.Bovo.modules.archive_detail.dto.response.MemoDTO;
import com.bovo.Bovo.modules.archive_detail.repository.ArchiveReadingNotesRepository;
import com.bovo.Bovo.modules.archive_detail.repository.ArchiveUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadingNotesService {

    private final ArchiveReadingNotesRepository readingNotesRepository;
    private final ArchiveMyBooksService myBooksService;
    private final ArchiveUsersRepository usersRepository;

    //메모 저장하기
    @Transactional
    public void save(Integer bookId, Integer userId, MemoCreateRequestDto requestDto){
        ReadingNotes memo = ReadingNotes.builder()
                .myBooks(myBooksService.myBookInfo(bookId, userId))
                .users(usersRepository.findOne(userId))
                .memoQuestion(requestDto.getMemoQ())
                .memoAnswer(requestDto.getMemoA())
                .recentlyCorrectionDate(myBooksService.stringToLocalDate(requestDto.getMemoDate()))
                .order(0)
                .build();
        readingNotesRepository.save(memo);
    }
    //책에서 메모리스트 가져오기
    public List<ReadingNotes> memosInfoByBook(Integer bookId, Integer userId) {
        MyBooks book = myBooksService.myBookInfo(bookId, userId);

        List<ReadingNotes> readingNotes = book.getReadingNotesList();

        readingNotes.sort(Comparator.comparing(ReadingNotes::getOrder,Comparator.nullsLast(Comparator.naturalOrder())));
                //.thenComparing(ReadingNotes::getId, Comparator.reverseOrder()));

        return readingNotes;
    }

    //메모 하나 보가
    public MemoDTO viewMemo(Integer bookId, Integer userId, Integer memoId ) {
        //메모 찾기
        ReadingNotes memo = readingNotesRepository.memoFindOne(memoId);

        //책 검증
        boolean validation = memo.getMyBooks().equals(myBooksService.myBookInfo(bookId, userId));

        //디티오 매핑
        if(validation) {
            return MemoDTO.builder()
                    .memoDate(myBooksService.localDateToString(memo.getRecentlyCorrectionDate()))
                    .memoQ(memo.getMemoQuestion())
                    .memoA(memo.getMemoAnswer())
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
                    , readingNote.getMemoQuestion()
                    , readingNote.getMemoAnswer());
            memos.add(memo);
        }
        return memos;
    }

    //메모 수정
    @Transactional
    public void updateMemo(Integer memoId, Integer bookId, Integer userId,MemoUpdateRequestDto requestDto) {
        //메모 찾기
        ReadingNotes memo = readingNotesRepository.memoFindOne(memoId);

        //책에 있는 메모인지 검증 로직
        boolean validation = memo.getMyBooks().equals(myBooksService.myBookInfo(bookId, userId));

        //메모 수정
        if(validation){
            memo.setMemoQuestion(requestDto.getMemoQ());
            memo.setMemoAnswer(requestDto.getMemoA());
        }else{
            throw new IllegalArgumentException("해당 메모를 수정할 권한이 없습니다.");
        }

    }

    //메모 삭제
    @Transactional
    public void deleteMemo(Integer memoId, Integer bookId, Integer userId) {
        //메모 찾기
        ReadingNotes memo = readingNotesRepository.memoFindOne(memoId);

        //책에 있는 메모인지 검증 로직
        boolean validation = memo.getMyBooks().equals(myBooksService.myBookInfo(bookId, userId));

        //책 삭제
        if(validation){
            readingNotesRepository.delete(memo);
        }else{
            throw new IllegalArgumentException("해당 메모를 삭제할 권한이 없습니다.");
        }


    }

    //메모 순서 변경
    @Transactional
    public void orderUpdateMemo(MemoOrderDTO requestDTO) {
        List<Integer> memoOrder = requestDTO.getMemoOrder();

        for (int i = 0; i < memoOrder.size(); i++) {
            ReadingNotes memo = readingNotesRepository.memoFindOne(memoOrder.get(i));
            memo.setOrder(i+1);

            System.out.println(memo.getMemoAnswer());

        }


    }


}
