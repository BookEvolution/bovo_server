package com.bovo.Bovo.modules.chatrooms.repository;

import com.bovo.Bovo.common.ChatMessage;
import com.bovo.Bovo.common.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 채팅방(roomId)의 메시지를 최근순으로 가져오기
    List<ChatMessage> findByChatRoomOrderByTimestampAsc(ChatRoom chatRoom);

    Optional<ChatMessage> findFirstByChatRoomOrderByTimestampDesc(ChatRoom chatRoom);

}