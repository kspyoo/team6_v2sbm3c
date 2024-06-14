package dev.mvc.masterlogin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class LoginMasterVO {
  
  /** PK*/
  private int masterloginno;
  /** 접속 IP*/
  private String ip = "";
  /** 접속 일자*/
  private String conndate="";
  /** FK*/
  private int masterno;
  /** 관리자 아이디*/
  private String masterid="";
  /** 관리자 이름 */
  private String mastername="";
  

  
  

}
