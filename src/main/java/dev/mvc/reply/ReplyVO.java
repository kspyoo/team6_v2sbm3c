package dev.mvc.reply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE REPLY(
//    REPLYNO                           NUMBER(10)     NULL      PRIMARY KEY,
//    CONTENT                           VARCHAR2(500)    NOT NULL,
//    RDATE                             DATE     NOT NULL,
//    COMMUNITYNO                       NUMBER(10)     NOT NULL,
//    MEMBERNO                          NUMBER(10)     NOT NULL,
//  FOREIGN KEY (COMMUNITYNO) REFERENCES COMMUNITY (COMMUNITYNO),
//  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
//);
@Getter
@Setter
@ToString
public class ReplyVO {
  private int replyno;
  private String content;
  private String rdate;
  private int communityno;
  private int memberno;
  
}
