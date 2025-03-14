package com.bovo.Bovo.modules.chatrooms.repository;

import com.bovo.Bovo.common.Participation;
import com.bovo.Bovo.common.ChatRoom;
import com.bovo.Bovo.common.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    // 특정 채팅방에 속한 참여자 조회
    List<Participation> findByChatRoom(ChatRoom chatRoom);

    // 특정 사용자가 참여한 모든 채팅방 조회
    List<Participation> findByUsers(Users users);

    // 특정 사용자가 특정 채팅방에 참여 여부 확인
    Optional<Participation> findByUsersAndChatRoom(Users users, ChatRoom chatRoom);

    // 특정 채팅방에서 방장 조회
    Optional<Participation> findByChatRoomAndIsLeader(ChatRoom chatRoom, Participation.LeaderStatus isLeader);

    // 특정 사용자가 방장인지 확인
    Optional<Participation> findByUsersAndChatRoomAndIsLeader(Users users, ChatRoom chatRoom, Participation.LeaderStatus isLeader);

    //특정 사용자가 속한 채팅룸 확인
    @Query("SELECT p.chatRoom FROM Participation p WHERE p.users = :user")
    List<ChatRoom> findChatRoomsByUser(@Param("user") Users user);
}