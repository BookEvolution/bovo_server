package com.bovo.Bovo.modules.archive.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.common.ReadingStatus;
import com.bovo.Bovo.modules.archive.dto.ArchiveDto;
import com.bovo.Bovo.modules.archive.dto.ArchiveResponseDto;
import com.bovo.Bovo.modules.archive.repository.ArchiveRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy. MM. dd");

    public ArchiveServiceImpl(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @Override
    public ArchiveResponseDto getMyBooks(Integer userId) {
        // 사용자 ID에 해당하는 내 서재의 책 목록을 조회
        List<MyBooks> myBooksList = archiveRepository.findByUsers_Id(userId);
        // 빈 리스트 처리
        if(myBooksList.isEmpty()) {
            System.out.println("조회 결과 없음");
        }

        // MyBooks 엔티티에서 필요한 필드만 추출하여 DTO로 변환
        List<ArchiveDto> books = myBooksList.stream()
                .map(book -> ArchiveDto.builder()
                        .bookId(book.getId())
                        .status(mapReadingStatus(book.getReadingStatus())) // enum을 별도의 문자열로 맵핑
                        .cover(book.getBookCover())
                        .title(book.getBookName())
                        .author(book.getBookAuthor())
                        .startDate(book.getReadingStartDate().format(formatter))
                        .star(book.getBookScore() != null ? book.getBookScore().multiply(BigDecimal.TWO).intValue() : 0)
                        .build())
                .collect(Collectors.toList());

        return ArchiveResponseDto.builder()
                .books(books)
                .build();
    }

    private String mapReadingStatus(ReadingStatus status) {
        switch (status) {
            case READING:
                return "ing";
            case COMPLETED:
                return "end";
            case WANT_TO_READ:
                return "wish";
            default:
                return "";
        }
    }
}