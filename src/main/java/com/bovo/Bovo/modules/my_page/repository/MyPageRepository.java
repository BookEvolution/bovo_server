package com.bovo.Bovo.modules.my_page.repository;

import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;

public interface MyPageRepository {
    PartialProfileDto findPartialByUserId(Integer userId);

    int countCompletedBooksByUserId(Integer userId);

    String findLastMedalByUserId(Integer userId);

    PartialProfileDetailDto findPartialProfileDetailByUserId(Integer userId);

    PartialProfileUpdateDto findPartialProfileUpdateByUserId(Integer userId);

    boolean newProfilePictureUpdate(String profile_picture, Integer userId);

    boolean newNicknameUpdate(String nickname, Integer userId);

    boolean newPassword(String password,Integer userId);
}
