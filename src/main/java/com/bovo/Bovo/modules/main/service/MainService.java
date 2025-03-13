package com.bovo.Bovo.modules.main.service;

import com.bovo.Bovo.modules.main.dto.response.partial.RecentBookInfoDto;
import com.bovo.Bovo.modules.main.dto.response.partial.UserInfoDto;

import java.util.Map;

public interface MainService {
    UserInfoDto getUserInfoByUserId(Integer userId);

    RecentBookInfoDto getRecentBookInfoByUserId(Integer userId);

    Map<String, String> getBookListByUserId(Integer userId);
}
