package com.bovo.Bovo.modules.main.service;

import com.bovo.Bovo.modules.main.dto.response.BookListDto;
import com.bovo.Bovo.modules.main.dto.response.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.UserInfoDto;
import com.bovo.Bovo.modules.main.repository.MainRepository;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {
    private final MainRepository mainRepository;

    public MainServiceImpl(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @Override
    public UserInfoDto getUserInfoByUserId(Integer userId) {
        return mainRepository.getUserInfoByUserId(userId);
    }

    @Override
    public RecentBookInfoDto getRecentBookInfoByUserId(Integer userId) {
        return mainRepository.getRecentBookInfoByUserId(userId);
    }

    @Override
    public BookListDto getBookListByUserId(Integer userId) {
        return mainRepository.getBookListByUserId(userId);
    }
}
