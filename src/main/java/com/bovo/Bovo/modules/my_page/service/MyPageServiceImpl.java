package com.bovo.Bovo.modules.my_page.service;

import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;
import com.bovo.Bovo.modules.my_page.repository.MyPageRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageServiceImpl implements MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageServiceImpl(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    @Override
    public PartialProfileDto findPartialMyPageByUserId(Integer userId) {
        return myPageRepository.findPartialByUserId(userId);
    }

    @Override
    public int countCompletedBooksByUserId(Integer userId) {
        return myPageRepository.countCompletedBooksByUserId(userId);
    }

    @Override
    public String findLastMedalByUserId(Integer userId) {
        return myPageRepository.findLastMedalByUserId(userId);
    }

    @Override
    public PartialProfileDetailDto findPartialProfileDetailByUserId(Integer userId) {
        return myPageRepository.findPartialProfileDetailByUserId(userId);
    }

    @Override
    public PartialProfileUpdateDto findPartialProfileUpdateByUserId(Integer userId) {
        return myPageRepository.findPartialProfileUpdateByUserId(userId);
    }

}
