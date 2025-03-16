package com.bovo.Bovo.modules.chatrooms.controller;

import com.bovo.Bovo.common.ChatRoom;
import com.bovo.Bovo.modules.chatrooms.dto.ChatMessageDTO;
import com.bovo.Bovo.modules.chatrooms.dto.request.CreateChatRoomRequestDTO;
import com.bovo.Bovo.modules.chatrooms.dto.request.SecretAnswerDTO;
import com.bovo.Bovo.modules.chatrooms.dto.response.*;
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

    @GetMapping("/my/{roomId}")
    public ResponseEntity<List<ChatMessageDTO>> joinMyChatroom(@AuthenticationPrincipal AuthenticatedUserId user, @PathVariable Integer roomId) {
        Integer userId = user.getUserId();

        //이전 채팅 메시지 전송
        List<ChatMessageDTO> messages = chatRoomService.getMessages(roomId);

        return ResponseEntity.ok(messages);
    }


    @PostMapping("/create")
    public CreateRoomDTO createChatroom(@AuthenticationPrincipal AuthenticatedUserId user, @RequestBody CreateChatRoomRequestDTO requestDTO) {
        Integer userId = user.getUserId();
        int  roomId = chatRoomService.createChatRoom(userId,requestDTO);
        return new CreateRoomDTO(roomId);
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

    // ✅ 채팅방에서 독서 기록 공유하기 (GET)
    @GetMapping("/memos")
    public ResponseEntity<List<ChatMemoDTO>> getChatMemos(@RequestParam("roomId") Integer roomId, @AuthenticationPrincipal AuthenticatedUserId user) {
        Integer userId = user.getUserId();
        List<ChatMemoDTO> memos = chatRoomService.getChatMemos(userId);
        return ResponseEntity.ok(memos);
    }

    // ✅ 토론방 사용자 리스트 조회 (GET)
    @GetMapping("/users")
    public ResponseEntity<List<ChatUserDTO>> getChatUsers(@RequestParam("roomId") Integer roomId) {
        List<ChatUserDTO> users = chatRoomService.getChatUsers(roomId);
        return ResponseEntity.ok(users);
    }

    // ✅ 채팅방 나가기 (DELETE)
    @DeleteMapping("/leave")
    public LeaveDTO leaveChatroom(
            @AuthenticationPrincipal AuthenticatedUserId user,
            @RequestParam("roomId") Integer roomId) {
        chatRoomService.leaveChatroom(user.getUserId(), roomId);
        return new LeaveDTO(200);
    }



}


