package com.bovo.Bovo.modules.main.dto.response;

import com.bovo.Bovo.modules.main.dto.response.partial.RecentBookInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TotalMainDto {
    private int status;
    private String message;
    private String profile_picture;
    private String nickname;
    private int level;
    private int total_book_num;
    private RecentBookInfoDto recent_book_info;
    private Map<String, String> book_list;
}
