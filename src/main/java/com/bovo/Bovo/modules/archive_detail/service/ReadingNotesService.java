package com.bovo.Bovo.modules.archive_detail.service;

import com.bovo.Bovo.modules.archive_detail.repository.ReadingNotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadingNotesService {

    private final ReadingNotesRepository readingNotesRepository;

    @Transactional
    public void save(){

    }

    public void memoInfo() {

    }

    @Transactional
    public void deleteMemo() {
    }

    public void showMemoList(){

    }

}
