package com.bovo.Bovo.modules.chatrooms.service;

import com.bovo.Bovo.common.ChatMessage;
import com.bovo.Bovo.common.ChatRoom;
import com.bovo.Bovo.common.Participation;
import com.bovo.Bovo.common.Users;
import com.bovo.Bovo.modules.chatrooms.dto.ChatMessageDTO;
import com.bovo.Bovo.modules.chatrooms.dto.request.CreateChatRoomRequestDTO;
import com.bovo.Bovo.modules.chatrooms.dto.response.*;
import com.bovo.Bovo.modules.chatrooms.repository.ChatMessageRepository;
import com.bovo.Bovo.modules.chatrooms.repository.ChatRoomRepository;
import com.bovo.Bovo.modules.chatrooms.repository.ChatroomUsersRepository;
import com.bovo.Bovo.modules.chatrooms.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ParticipationRepository participationRepository;
    private final ChatroomUsersRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    //챗룸 가져오기
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    //유저 챗룸 가져오기
    public List<ChatRoom> getMyChatRooms(Integer userId) {
        return participationRepository.findChatRoomsByUser(userRepository.getReferenceById(userId));
    }

    public List<MyChatRoomResponseDTO> mapMyChatRoomDTO(List<ChatRoom> chatRooms) {
        List<MyChatRoomResponseDTO> myChatRoomResponseDTOList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            Optional<ChatMessage> lastMessage = chatMessageRepository.findFirstByChatRoomOrderByTimestampDesc(chatRoom);

            String message = lastMessage
                    .map(ChatMessage::getMessage)
                    .orElse("최근 메시지가 없습니다.");
            LocalDateTime time = lastMessage
                    .map(ChatMessage::getTimestamp)
                    .orElse(LocalDateTime.now());


            MyChatRoomResponseDTO temp = MyChatRoomResponseDTO.builder()
                    .id(chatRoom.getId())
                    .chatroomName(chatRoom.getChatName())
                    .bookInfo(MyChatRoomResponseDTO.BookInfo.builder()
                            .bookImg(chatRoom.getBookCover())
                            .build())
                    .lastMsgInfo(MyChatRoomResponseDTO.LastMessageInfo.builder()
                            .lastMessage(message)
                            .lastMessageDate(time)
                            .build())
                    .noReadingCount(0)
                    .build();
            myChatRoomResponseDTOList.add(temp);

        }

        return myChatRoomResponseDTOList;
    }

    //챗룸 디티오 매핑
    public ChatRoomListDTO ChatRoomToChatRoomListDto(List<ChatRoom> chatRoomList) {
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();


        for (ChatRoom chatRoom : chatRoomList) {
            ChatRoomDTO chatRoomDTO = getChatRoomDTO(chatRoom);
            chatRoomDTOList.add(chatRoomDTO);
        }

        return new ChatRoomListDTO(chatRoomDTOList);
    }

    private ChatRoomDTO getChatRoomDTO(ChatRoom chatRoom) {
        return ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .chatroomName(chatRoom.getChatName())
                .chatroomDs(chatRoom.getChatInfo())
                .bookInfo(ChatRoomDTO.BookInfo.builder()
                        .bookImg(chatRoom.getBookCover())
                        .bookName(chatRoom.getBookName())
                        .build())
                .duration(ChatRoomDTO.Duration.builder()
                        .startDate(chatRoom.getChallengeStartDate())
                        .endDate(chatRoom.getChallengeEndDate())
                        .build())
                .groupInfo(ChatRoomDTO.GroupInfo.builder()
                        .limitPeople(chatRoom.getMaxRecruiting())
                        .currentPeople(participationRepository.findByChatRoom(chatRoom).size())
                        .build())
                .admin(chatRoom.getAdmin())
                .build();
    }

    @Transactional
    public void createChatRoom(Integer userId, CreateChatRoomRequestDTO requestDTO) {
        CreateChatRoomRequestDTO.BookInfoDTO bookInfo = requestDTO.getBookInfo();
        CreateChatRoomRequestDTO.ChatInfoDTO chatInfo = requestDTO.getChatInfo();
        ChatRoom chatRoom = ChatRoom.builder()
                .bookName(bookInfo.getBookName())
                .bookAuthor(bookInfo.getBookAuthor())
                .bookCover(bookInfo.getBookCover())
                .chatName(chatInfo.getChatName())
                .chatInfo(chatInfo.getChatDetail())
                .maxRecruiting(chatInfo.getMaxRecruiting())
                .challengeStartDate(chatInfo.getChallengeStartDate())
                .challengeEndDate(chatInfo.getChallengeEndDate())
                .isSecret(setSecret(chatInfo))
                .secretQuestion(chatInfo.getSecretQuestion())
                .admin("false")
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);

        //유저 찾고 participation 저장
        joinChatroom(userId, chatRoom.getId(), chatInfo.getSecretAnswer(), true);
    }

    private ChatRoom.SecretType setSecret(CreateChatRoomRequestDTO.ChatInfoDTO chatInfo) {
        if (chatInfo.isSecret()) {
            return ChatRoom.SecretType.YES;
        }
        return ChatRoom.SecretType.NO;
    }



    @Transactional
    public void joinChatroom(Integer userId, Integer chatRoomId, String answer, boolean isLeader) {
        //유저 찾기
        Users user = userRepository.getReferenceById(userId);

        //채팅방 찾기
        ChatRoom chatRoom = chatRoomRepository.getReferenceById(chatRoomId);

        //권한 있는지 확인
//        if(validationJoinChatroom(user, chatRoom)){
//            return;
//        }

        //방장과 답변 비교
        if(!isLeader){
            Optional<Participation> roomLeader = participationRepository.findByChatRoomAndIsLeader(chatRoom, Participation.LeaderStatus.YES);
            String leaderAnswer = roomLeader.map(Participation::getAnswer).orElse("");
            if(!leaderAnswer.equals(answer)){
                throw new RuntimeException("비밀방과 답변이 다릅니다.");
            }

        }

        //유저를 participation에 저장
        Participation participation = Participation.builder()
                .chatRoom(chatRoom)
                .users(user)
                .answer(answer)
                .isLeader(setIsLeader(isLeader))
                .build();

        participationRepository.save(participation);

    }

    private static Participation.LeaderStatus setIsLeader(boolean isLeader) {
        if(isLeader){
            return Participation.LeaderStatus.YES;
        }
        return Participation.LeaderStatus.NO;
    }

    @Transactional
    public void leaveChatroom(Integer userId, Integer roomId) {

    }

    private boolean validationJoinChatroom(Users user, ChatRoom chatRoom){
        if(participationRepository.findByUsersAndChatRoom(user, chatRoom).isEmpty()){
            return false;
        }
        return true;
    }

    public GetChatInfoModalDTO mapGetChatInfoModalDTO(Integer roomId) {
        ChatRoom chatRoom = chatRoomRepository.getReferenceById(roomId);

        int currentPeople = participationRepository.findByChatRoom(chatRoom).size();

        String answer = participationRepository.findByChatRoomAndIsLeader(chatRoom, Participation.LeaderStatus.YES).get().getAnswer();

        GetChatInfoModalDTO getChatInfoModalDTO = GetChatInfoModalDTO.builder()
                .bookInfo(GetChatInfoModalDTO.BookInfoDTO.builder()
                        .bookName(chatRoom.getBookName())
                        .bookCover(chatRoom.getBookCover())
                        .build())
                .chatInfo(GetChatInfoModalDTO.ChatInfoDTO.builder()
                        .chatName(chatRoom.getChatName())
                        .chatDetail(chatRoom.getChatInfo())
                        .challengeStartDate(chatRoom.getChallengeStartDate())
                        .challengeEndDate(chatRoom.getChallengeEndDate())
                        .limitPeople(chatRoom.getMaxRecruiting())
                        .currentPeople(currentPeople)
                        .isSecret(getSecret(chatRoom))
                        .secretQuestion(chatRoom.getSecretQuestion())
                        .secretAnswer(answer)
                        .build())
                .build();


        return getChatInfoModalDTO;
    }
    private boolean getSecret(ChatRoom chatRoom){
        if (chatRoom.getIsSecret() == ChatRoom.SecretType.YES) {
            return true;
        }
        return false;
    }

    public List<ChatMessageDTO> getMessages(Integer roomId) {

        ChatRoom chatRoom = chatRoomRepository.getReferenceById(roomId);
        List<ChatMessage> chatMessages = chatRoom.getChatMessages();

        List<ChatMessageDTO> responseDTO = new ArrayList<>();


        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                    .id(chatMessage.getId())
                    .timestamp(chatMessage.getTimestamp())
                    .messageType(chatMessage.getType().toString())
                    .message(chatMessage.getMessage())
                    .chatRoomId(chatMessage.getChatRoom().getId())
                    .nickname(chatMessage.getUsers().getNickname())
                    .email(chatMessage.getUsers().getEmail())
                    .build();

            responseDTO.add(chatMessageDTO);
        }


        return responseDTO;
    }


}
