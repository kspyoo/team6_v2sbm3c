package dev.mvc.jpa.message;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select msg from Message msg " +
            "where msg.chatroom.chatroomno = :chatroomno " +
            "order by msg.messageno desc")
    Message findLastMessage(@Param("chatroomno") int chatroomno, PageRequest pageable); // PageRequest.of(0, 1)를 파라미터로 사용하면 0페이지의 1번까지 반환 시켜줌

    @Query("select count(msg) from Message msg " +
            "where msg.chatroom.chatroomno = :chatroomno " +
            "and msg.isreaded = 0 " +
            "and not msg.sender.memberno = :memberno")
    int countMessage(@Param("chatroomno") int chatroomno, @Param("memberno") int memberno);

    @Query("select msg from Message msg " +
            "where msg.chatroom.chatroomno = :chatroomno " +
            "order by msg.messageno")
    List<Message> findByChatRoomNo(@Param("chatroomno") int chatroomno);

    @Modifying
    @Transactional
    @Query("update Message set isreaded = 1 where chatroom.chatroomno = :chatroomno and not sender.memberno = :memberno")
    void readMsg(@Param("chatroomno") int chatroomno , @Param("memberno") int memberno);
}
