package com.bovo.Bovo.modules.my_page.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalProfileDto {
    private int status;
    private String message;
    private String profile_picture;
    private String nickname;
    private int level;
    private int exp;
    private int total_book_num;
    private String medal;
}
