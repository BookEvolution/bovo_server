package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.domain.Users;

public interface UserRepository {
    Users save(Users user); // 회원가입: user를 DB에 저장

    boolean existNickname(String nickname);
    // 추가: user 삭제
}
