package com.bovo.Bovo.modules.chatrooms.repository;

import com.bovo.Bovo.common.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatroomUsersRepository extends JpaRepository<Users, Integer> {

    // 이메일로 사용자 조회
    Optional<Users> findByEmail(String email);

    // 닉네임으로 사용자 조회
    Optional<Users> findByNickname(String nickname);

    // 닉네임 포함 검색 (부분 검색)
    Optional<Users> findByNicknameContaining(String nickname);
}