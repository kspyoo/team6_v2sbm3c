package dev.mvc.jpa.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageForm {
    private int sender;
    private int chatRoomNo;
    private String content;
}
