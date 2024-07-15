package dev.mvc.team6_v2sbm3c;

import dev.mvc.jpa.chatroom.ChatRoomRepository;
import dev.mvc.jpa.chatroomuser.ChatRoomUserRepository;
import dev.mvc.jpa.chatroomuser.Chatroomuser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ChatTest {

    @Autowired
    private ChatRoomRepository crRepo;

    @Autowired
    private ChatRoomUserRepository cruRepo;

    @Test
    public void test(){
        List<Chatroomuser> list = cruRepo.findUsersByMemberNo(3);
        for (Chatroomuser a :list){
            System.out.println(a.getChatroom().getChatroomno());
            System.out.println(a.getMember().getName());
        }
    }
}
