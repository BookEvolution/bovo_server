package com.bovo.Bovo.modules.archive.service;

import com.bovo.Bovo.common.MyBooks;
import com.bovo.Bovo.modules.archive.dto.ArchiveDto;
import com.bovo.Bovo.modules.archive.dto.ArchiveResponseDto;
import com.bovo.Bovo.modules.archive.repository.ArchiveRepository;
import org.springframework.stereotype.Service;

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

    // userId로 내 서재 조회
    @Override
    public ArchiveResponseDto getMyBooks(Integer userId) {
        List<MyBooks> myBooksList = archiveRepository.findByUsers_Id(userId);

        // MyBooks의 필드값을 DTO로 전달
        List<ArchiveDto> books = myBooksList.stream()
                .map(book -> ArchiveDto.builder()
                        .bookId(book.getId())
                        .status(String.valueOf(book.getReadingStatus()))
                        .cover(book.getBookCover())
                        .title(book.getBookName())
                        .author(book.getBookAuthor())
                        .startDate(book.getReadingStartDate().format(formatter))
                        .star(book.getBookScore() != null ? book.getBookScore().intValue() : 0) // null 대비
                        .build())
                .collect(Collectors.toList());

        return ArchiveResponseDto.builder()
                .books(books)
                .build();
    }
}
