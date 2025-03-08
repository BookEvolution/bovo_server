package com.bovo.Bovo.modules.my_page.service;

import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileDetailDto;
import com.bovo.Bovo.modules.my_page.dto.response.partial.PartialProfileUpdateDto;

public interface MyPageService {
    PartialProfileDto findPartialMyPageByUserId(Integer userId);

    int countCompletedBooksByUserId(Integer userId);

    String findLastMedalByUserId(Integer userId);

    PartialProfileDetailDto findPartialProfileDetailByUserId(Integer userId);

    PartialProfileUpdateDto findPartialProfileUpdateByUserId(Integer userId);
}
