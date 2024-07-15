package dev.mvc.jpa.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.mvc.jpa.chatroom.Chatroom;
import dev.mvc.jpa.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MASSAGE_SEQ")
    @SequenceGenerator(name="MASSAGE_SEQ", sequenceName="MASSAGE_SEQ", allocationSize=1)
    private int messageno;

    @ManyToOne
    @JoinColumn(name="sender")
    @JsonIgnoreProperties({"messages"}) // sender에서 messages 필드는 무시
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "chatroom", nullable = false)
    @JsonIgnoreProperties({"messages"}) // sender에서 messages 필드는 무시
    private Chatroom chatroom;

    private String content;

    private String regdate;

    private int isreaded=0;

    public Message(Member sender, Chatroom chatRoom, String content, String regdate) {
        this.sender = sender;
        this.chatroom = chatRoom;
        this.content = content;
        this.regdate = regdate;
    }

}
