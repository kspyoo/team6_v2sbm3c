package dev.mvc.jpa.chatroom;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.mvc.jpa.chatroomuser.Chatroomuser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Chatroom {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHATROOM_SEQ")
    @SequenceGenerator(name="CHATROOM_SEQ", sequenceName="CHATROOM_SEQ", allocationSize=1)
    private int chatroomno;

    private String createddate;

    // 조회시 조인을 하기위해 Chatroomuser와 연결된 컬럼을 지정해줌
    @OneToMany(mappedBy = "chatroom")
    @JsonManagedReference // 순환 참조 방지를 위한 Jackson 어노테이션
    private List<Chatroomuser> chatroomuser;

    public Chatroom(String createdDate) {
        this.createddate = createdDate;
    }
}
