package dev.mvc.jpa.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<Chatroom, Long> {

    // 채팅방 리스트 조회
    @Query("select cr from Chatroom cr " +
            "join Chatroomuser cu on cu.chatroomuserid.chatroomno = cr.chatroomno and cu.chatroomuserid.memberno = :memberNo")
    List<Chatroom> myChattingList(@Param("memberNo") int memberNo);

    @Query("select cr, cu1, cu2 from Chatroom cr " +
            "join Chatroomuser cu1 on cu1.chatroomuserid.chatroomno = cr.chatroomno and cu1.chatroomuserid.memberno = :memberNo "+
            "join Chatroomuser cu2 on cu2.chatroomuserid.chatroomno = cr.chatroomno")
    List<Object> listTest(@Param("memberNo") int memberNo);

    // 특정 유저와 함께있는 채팅방이 있는지 확인
    // Optional : 안전하게 null체크를 하기위해 사용
    @Query("select cr from Chatroom cr " +
            "join Chatroomuser cu1 on cu1.chatroomuserid.chatroomno = cr.chatroomno and cu1.chatroomuserid.memberno = :#{#map['sender']} " +
            "join Chatroomuser cu2 on cu2.chatroomuserid.chatroomno = cr.chatroomno and cu2.chatroomuserid.memberno = :#{#map['receiver']}")
    Optional<Chatroom> findChatRoomsByUserNo(@Param("map") Map<String,Object> map);

    Optional<Chatroom> findByChatroomno(int Chatroomno);

}
