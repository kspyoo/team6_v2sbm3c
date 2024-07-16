package dev.mvc.reply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReplyMemberVO {
  
  private String id = "";
  
  private String name = "";
  
  private int replyno;
  
  private int communityno;
  
  private int memberno;
  
  private String content;
  
  private String rdate;
}
