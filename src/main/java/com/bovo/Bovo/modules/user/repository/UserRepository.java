package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.Users;

public interface UserRepository {
    Users save(Users user); // 회원가입: user를 DB에 저장

    boolean existNickname(String nickname);

    Users deleteUser(Users user);// user 삭제
}
