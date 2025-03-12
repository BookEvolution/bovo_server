package com.bovo.Bovo.modules.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private BookListDto book_list;
}
