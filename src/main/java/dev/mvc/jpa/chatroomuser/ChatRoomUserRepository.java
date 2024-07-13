package dev.mvc.jpa.chatroomuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<Chatroomuser, Long> {

    // Chatroomuser 엔티티에있는 chatroom 엔티티와 조인을 하여 해당 채팅방에 있는 유저 정보를 가져옴(본인 제외)
    @Query("SELECT cru FROM Chatroomuser cru " +
            "JOIN cru.chatroom cr " +
            "WHERE cr.chatroomno = :chatroomno and" +
            " not cru.member.memberno = :memberno")
    List<Chatroomuser> findUsersByChatRoomNo(@Param("chatroomno") int chatRoomNo, @Param("memberno") int memberNo);

    @Query("SELECT cru FROM Chatroomuser cru " +
            "WHERE cru.chatroom.chatroomno IN " +
            "(SELECT cu.chatroom.chatroomno FROM Chatroomuser cu WHERE cu.member.memberno = :memberno) " +
            "AND cru.member.memberno <> :memberno")
    List<Chatroomuser> findUsersByMemberNo(@Param("memberno") int memberNo);
}
