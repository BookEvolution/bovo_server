package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.modules.user.domain.User;
import com.bovo.Bovo.modules.user.domain.UserAuth;

import java.util.Optional;

public interface UserAuthRepository {
    boolean existEmail(String email);
    UserAuth save(UserAuth usera); // 회원가입: usera를 DB에 저장
    Optional<UserAuth> findByEmail(String email); // email로 user 조회
}
