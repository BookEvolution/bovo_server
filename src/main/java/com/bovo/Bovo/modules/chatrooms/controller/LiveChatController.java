package com.bovo.Bovo.modules.chatrooms.controller;

import com.bovo.Bovo.common.ChatMessage;
import com.bovo.Bovo.common.ChatRoom;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.chatrooms.dto.ChatMessageDTO;
import com.bovo.Bovo.modules.chatrooms.repository.ChatMessageRepository;
import com.bovo.Bovo.modules.chatrooms.repository.ChatRoomRepository;
import com.bovo.Bovo.modules.chatrooms.repository.ChatroomUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LiveChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatroomUsersRepository usersRepository;

    @MessageMapping("/chatroom/{roomId}")
    public void sendMessage(@DestinationVariable Integer roomId, ChatMessage message) {
        log.info("받은 메시지: {}", message);

        Optional<Users> userOptional = usersRepository.findById(message.getUsers().getId());
        if (userOptional.isEmpty()) {
            log.error("유저를 찾을 수 없습니다. ID: {}", message.getUsers().getId());
            return;
        }

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(roomId);
        if (chatRoomOptional.isEmpty()) {
            log.error("채팅방을 찾을 수 없습니다. Room ID: {}", roomId);
            return;
        }

        Users user = userOptional.get();
        ChatRoom chatRoom = chatRoomOptional.get();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUsers(user);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setMessage(message.getMessage());
        chatMessage.setType(message.getType());
        chatMessage.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        log.info("저장된 메시지: {}", savedMessage);

        // 🔥 DTO 변환 후 WebSocket 전송
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .id(savedMessage.getId())
                .message(savedMessage.getMessage())
                .timestamp(savedMessage.getTimestamp())
                .messageType(savedMessage.getType().name())
                .userId(user.getId())
                .nickname(user.getNickname())  // 닉네임 포함
                .chatRoomId(chatRoom.getId())
                .build();

        messagingTemplate.convertAndSend("/topic/chatroom/" + roomId, chatMessageDTO);
    }
}
