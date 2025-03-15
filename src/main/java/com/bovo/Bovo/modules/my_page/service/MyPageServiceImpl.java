package com.bovo.Bovo.modules.my_page.service;

import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;
import com.bovo.Bovo.modules.my_page.repository.MyPageRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPageServiceImpl implements MyPageService {
    private final MyPageRepository myPageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MyPageServiceImpl(MyPageRepository myPageRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.myPageRepository = myPageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    @Override
    public boolean newProfileUpdate(String profile_picture, String nickname, String password, Integer userId) {
        boolean p_update = true;
        boolean n_update = true;
        boolean w_update = true;
        if (profile_picture != null) {
            System.out.println("Service - profile_picture = " + profile_picture);
            p_update = myPageRepository.newProfilePictureUpdate(profile_picture, userId);
        }
        if (nickname != null) {
            n_update = myPageRepository.newNicknameUpdate(nickname, userId);
        }
        if (password != null) {
            w_update = myPageRepository.newPassword(bCryptPasswordEncoder.encode(password), userId);
        }

        if (p_update && n_update && w_update) {
            return true;
        } else {
            return false;
        }
    }

}
