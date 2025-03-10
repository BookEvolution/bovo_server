package com.bovo.Bovo.modules.archive.service;

import com.bovo.Bovo.modules.archive.dto.ArchiveResponseDto;

public interface ArchiveService {
    ArchiveResponseDto getMyBooks(Integer userId);
}
