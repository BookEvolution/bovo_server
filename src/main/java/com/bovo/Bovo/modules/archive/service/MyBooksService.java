package com.bovo.Bovo.modules.archive.service;

import com.bovo.Bovo.modules.archive.dto.Response.MyBooksResponse;

public interface MyBooksService {
    MyBooksResponse getMyBooks(Integer userId);
}
