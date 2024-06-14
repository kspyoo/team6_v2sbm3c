package dev.mvc.masterlogin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MasterloginVO {
  /** PK*/
  private int masterloginno;
  /** 접속 IP*/
  private String ip = "";
  /** 접속 일자*/
  private String conndate="";
  /** FK*/
  private int masterno;
  
  private String masterid="";
  
  private String mastername="";
  


}
