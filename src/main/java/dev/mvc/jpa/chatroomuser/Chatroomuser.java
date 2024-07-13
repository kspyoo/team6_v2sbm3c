package dev.mvc.jpa.chatroomuser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.mvc.jpa.chatroom.Chatroom;
import dev.mvc.jpa.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Chatroomuser {
    @EmbeddedId // 복합키로 만들기위해 제작한 클래스를 테이블의 부모키로 사용하게 만들어줌
    private Chatroomuserid chatroomuserid;

    @MapsId("chatroomno") // 복합키로 사용중인 객체 안에 어떤 변수와 매핑이되는지 정해줌
    @ManyToOne
    @JoinColumn(name = "chatroomno", insertable = false, updatable = false)
    @JsonBackReference // 순환 참조 방지를 위한 Jackson 어노테이션
    private Chatroom chatroom;

    @MapsId("memberno")
    @ManyToOne
    @JoinColumn(name = "memberno", insertable = false, updatable = false)
    private Member member;

    public Chatroomuser(Chatroom chatRoom, Member member) {
        this.chatroom = chatRoom;
        this.member = member;
        this.chatroomuserid = new Chatroomuserid(chatRoom.getChatroomno(), member.getMemberno());
    }
}
