package dev.mvc.jpa.chatroomuser;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class Chatroomuserid implements Serializable {
    private int chatroomno;
    private int memberno;

    public Chatroomuserid(int chatroomno, int memberno) {
        this.chatroomno = chatroomno;
        this.memberno = memberno;
    }
}
