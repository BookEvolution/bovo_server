package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.domain.User_Auth;

import java.util.Optional;

public interface UserAuthRepository {
    boolean existEmail(String email);
    User_Auth save(User_Auth usera); // 회원가입: usera를 DB에 저장
    Optional<User_Auth> findByEmail(String email); // email로 user 조회
}
