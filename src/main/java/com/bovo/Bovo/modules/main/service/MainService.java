package com.bovo.Bovo.modules.main.service;

import com.bovo.Bovo.modules.main.dto.response.BookListDto;
import com.bovo.Bovo.modules.main.dto.response.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.UserInfoDto;

public interface MainService {
    UserInfoDto getUserInfoByUserId(Integer userId);

    RecentBookInfoDto getRecentBookInfoByUserId(Integer userId);

    BookListDto getBookListByUserId(Integer userId);
}
