package dev.mvc.jpa.message;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageDTO {

    private int messageno;

    private int sender;

    private int chatroom;

    private String content;

    private String regdate;

    private int isreaded=0;
}
