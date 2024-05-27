package dev.mvc.login;

import java.util.Date;

import dev.mvc.member.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LoginVO {
  /** PK*/
  private int loginno;
  /** 접속 IP*/
  private String ip = "";
  /** 접속 일자*/
  private String conndate="";
  /** FK*/
  private int memberno;
  
  private String id="";
  
  private String name="";
  
  private int mprofileno=0;
}
  