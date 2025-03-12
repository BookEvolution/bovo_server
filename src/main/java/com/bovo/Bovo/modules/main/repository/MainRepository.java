package com.bovo.Bovo.modules.main.repository;

import com.bovo.Bovo.modules.main.dto.response.BookListDto;
import com.bovo.Bovo.modules.main.dto.response.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.UserInfoDto;

public interface MainRepository {
    UserInfoDto getUserInfoByUserId(Integer userId);

    RecentBookInfoDto getRecentBookInfoByUserId(Integer userId);

    BookListDto getBookListByUserId(Integer userId);
}
