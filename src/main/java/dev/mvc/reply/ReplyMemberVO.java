package dev.mvc.reply;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReplyMemberVO {
  
  private String id = "";
  
  private int replyno;
  
  private int communityno;
  
  private int memberno;
  
  private String content;
  
  private String rdate;
}
