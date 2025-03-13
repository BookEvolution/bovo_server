package com.bovo.Bovo.modules.main.repository;

import com.bovo.Bovo.modules.main.dto.response.partial.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.partial.UserInfoDto;

import java.util.Map;

public interface MainRepository {
    UserInfoDto getUserInfoByUserId(Integer userId);

    RecentBookInfoDto getRecentBookInfoByUserId(Integer userId);

    Map<String, String> getBookListByUserId(Integer userId);
}
