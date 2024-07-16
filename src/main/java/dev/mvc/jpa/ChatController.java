package dev.mvc.jpa;

import dev.mvc.jpa.chatroom.Chatroom;
import dev.mvc.jpa.chatroom.ChatRoomRepository;
import dev.mvc.jpa.chatroomuser.Chatroomuser;
import dev.mvc.jpa.chatroomuser.ChatRoomUserRepository;
import dev.mvc.jpa.member.Member;
import dev.mvc.jpa.member.MemberRepository;
import dev.mvc.jpa.message.Message;
import dev.mvc.jpa.message.MessageDTO;
import dev.mvc.jpa.message.MessageForm;
import dev.mvc.jpa.message.MessageRepository;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageRepository msgRepo;
    private final MemberRepository memRepo;
    private final ChatRoomRepository crRepo;
    private final ChatRoomUserRepository cruRepo;

    @GetMapping("/chatting")
    public String chat(Model model, HttpSession session,
                       @RequestParam(name = "receiverNo", defaultValue = "0") int receiverNo){
        if(session.getAttribute("memberno") == null){
            return "redirect:/member/login";
        }

        int memberNo = (int) session.getAttribute("memberno");
        int chatRoomNo = 0;

        // 특정 유저와 채팅을 하기위해 들어오는 경우
        if(receiverNo > 0){
            System.out.println("유저있음");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("sender", memberNo);
            map.put("receiver", receiverNo);
            Optional<Chatroom> chatRoom = crRepo.findChatRoomsByUserNo(map);

            // 해당 유저와의 채팅방이 없는경우
            if (chatRoom.isEmpty()){
                // 채팅방 생성
                Chatroom cr = new Chatroom(Tool.getDate());
                Chatroom savedCr = crRepo.save(cr); // db에 저장된 엔티티를 저장

                //채팅방의 유저(본인) 추가
                Member member = memRepo.findByMemberno(memberNo);
                Chatroomuser cru = new Chatroomuser(savedCr, member);
                cruRepo.save(cru);

                //채팅방의 유저(상대) 추가
                member = memRepo.findByMemberno(receiverNo);
                cru = new Chatroomuser(savedCr, member);
                cruRepo.save(cru);
                System.out.println("생성됨");

                chatRoomNo = savedCr.getChatroomno();
            }else{
                chatRoomNo = chatRoom.get().getChatroomno();
            }
        }

//        model.addAttribute("senderNo", memberNo);
//        model.addAttribute("receiverNo", receiverNo);
        model.addAttribute("chatRoomNo", chatRoomNo);

        return "chat/chatting";
    }

    @GetMapping("/chatting/list")
    @ResponseBody
    public ResponseEntity<List<Chatroomuser>> chatList(HttpSession session){
        int memberNo = (int) session.getAttribute("memberno");
        List<Chatroomuser> list = cruRepo.findUsersByMemberNo(memberNo);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/chatting/lastMsg")
    @ResponseBody
    public ResponseEntity<Message> lastMsg(int chatRoomNo){
        Message msg = msgRepo.findLastMessage(chatRoomNo, PageRequest.of(0, 1));
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @GetMapping("/chatting/countMessage")
    @ResponseBody
    public ResponseEntity<String> countMessage(int chatRoomNo, HttpSession session){
        int memberNo = (int) session.getAttribute("memberno");

        int msg = msgRepo.countMessage(chatRoomNo, memberNo);

        JSONObject json = new JSONObject();
        json.put("count", msg);

        return ResponseEntity.status(HttpStatus.OK).body(json.toString());
    }

    @GetMapping("/chatting/countAndMsg")
    @ResponseBody
    public ResponseEntity<String> countAndMsg(int chatRoomNo, HttpSession session){
        int memberNo = (int) session.getAttribute("memberno");
        int cnt = msgRepo.countMessage(chatRoomNo, memberNo);
        Message msg = msgRepo.findLastMessage(chatRoomNo, PageRequest.of(0, 1));
        
        JSONObject json = new JSONObject();
        if(cnt != 0) {
            json.put("content", msg.getContent());
            json.put("regdate", msg.getRegdate());
            json.put("count", cnt);
        }else{
            if (msg == null){
                json.put("content", "마지막 메세지없음");
                json.put("regdate", "");
            }else {
                json.put("content", msg.getContent());
                json.put("regdate", msg.getRegdate());
            }
            json.put("count", cnt);
        }

        return ResponseEntity.status(HttpStatus.OK).body(json.toString());
    }

    @GetMapping("/chatting/msgList")
    @ResponseBody
    public ResponseEntity<List<MessageDTO>> msgList(int chatRoomNo){
        List<Message> list = msgRepo.findByChatRoomNo(chatRoomNo);
        List<MessageDTO> dtoList = new ArrayList<MessageDTO>();

        for(Message msg:list){
            MessageDTO dto = new MessageDTO();

            dto.setMessageno(msg.getMessageno());
            dto.setSender(msg.getSender().getMemberno());
            dto.setChatroom(msg.getChatroom().getChatroomno());
            dto.setContent(msg.getContent());
            dto.setRegdate(msg.getRegdate());
            dto.setIsreaded(msg.getIsreaded());

            dtoList.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @GetMapping("/chatting/readMsg")
    @ResponseBody
    public void readMsg(int chatRoomNo, HttpSession session){
        int memberNo = (int) session.getAttribute("memberno");
        msgRepo.readMsg(chatRoomNo, memberNo);
    }

    @MessageMapping("/chat/{chatRoomNo}") // '/chat'으로 소켓 요청이있으면 실행
    @SendTo("/topic/messages/{chatRoomNo}")
    public MessageDTO send(@DestinationVariable String chatRoomNo, MessageForm message) throws Exception {
        int senderNo = message.getSender();
        String content = message.getContent();
        int crNo = message.getChatRoomNo();

        Member sender = memRepo.findByMemberno(senderNo);
        Optional<Chatroom> chatroom = crRepo.findByChatroomno(crNo);

        Message msg = new Message(sender, chatroom.get(), content, Tool.getDateAndTime());

        msg = msgRepo.save(msg);

        MessageDTO sendMsg = new MessageDTO();
        sendMsg.setSender(msg.getSender().getMemberno());
        sendMsg.setChatroom(msg.getChatroom().getChatroomno());
        sendMsg.setContent(msg.getContent());
        sendMsg.setRegdate(msg.getRegdate());
        // 메시지를 특정 사용자에게 전송
//        messagingTemplate.convertAndSendToUser(String.valueOf(to), "/queue/messages", message);
        return sendMsg;
    }
}
