package com.bovo.Bovo.modules.chatrooms.repository;

import com.bovo.Bovo.common.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    // 특정 채팅방을 ID로 조회
    Optional<ChatRoom> findById(Integer id);

}
