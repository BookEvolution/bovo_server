package com.bovo.Bovo.modules.chatrooms.controller;

import com.bovo.Bovo.common.ChatRoom;
import com.bovo.Bovo.modules.chatrooms.dto.ChatMessageDTO;
import com.bovo.Bovo.modules.chatrooms.dto.request.CreateChatRoomRequestDTO;
import com.bovo.Bovo.modules.chatrooms.dto.request.SecretAnswerDTO;
import com.bovo.Bovo.modules.chatrooms.dto.response.ChatRoomListDTO;
import com.bovo.Bovo.modules.chatrooms.dto.response.GetChatInfoModalDTO;
import com.bovo.Bovo.modules.chatrooms.dto.response.MyChatRoomResponseDTO;
import com.bovo.Bovo.modules.chatrooms.service.ChatRoomService;
import com.bovo.Bovo.modules.rewards.service.ExpIncService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatroomController {



    private final ChatRoomService chatRoomService;
    private final ExpIncService expIncService;

    @GetMapping
    public ChatRoomListDTO getAllChatroomList() {
        List<ChatRoom> allChatRooms = chatRoomService.getAllChatRooms();

        return chatRoomService.ChatRoomToChatRoomListDto(allChatRooms);
    }

    @GetMapping("/my")
    public List<MyChatRoomResponseDTO> getMyChatroomList(@AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        List<ChatRoom> myChatRooms = Objects.requireNonNullElse(chatRoomService.getMyChatRooms(userId),
                Collections.emptyList());




        return chatRoomService.mapMyChatRoomDTO(myChatRooms);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createChatroom(@AuthenticationPrincipal AuthenticatedUserId user, @RequestBody CreateChatRoomRequestDTO requestDTO) {
        Integer userId = user.getUserId();
        chatRoomService.createChatRoom(userId,requestDTO);
        return ResponseEntity.ok().build();
    }


    //채팅방 모달 띄우기
    @GetMapping("/{roomId}")
    public GetChatInfoModalDTO getChatroomModal(@AuthenticationPrincipal AuthenticatedUserId user, @PathVariable Integer roomId) {
        chatRoomService.mapGetChatInfoModalDTO(roomId);

        return chatRoomService.mapGetChatInfoModalDTO(roomId);
    }


    //채팅방 참여하기 버튼
    @PostMapping
    public ResponseEntity<List<ChatMessageDTO>> joinChatroom(@AuthenticationPrincipal AuthenticatedUserId user, @RequestParam Integer roomId, @RequestBody SecretAnswerDTO requestDTO){
        Integer userId = user.getUserId();

        //없으면 비밀방 답변후 권한 부여
        chatRoomService.joinChatroom(userId, roomId, requestDTO.getSecretAnswer(), false);

        //이전 채팅 메시지 전송
        List<ChatMessageDTO> messages = chatRoomService.getMessages(roomId);
        expIncService.updateExp(userId, 2);

        return ResponseEntity.ok(messages);
    }



}


