package dev.mvc.master;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MasterVO {
 /*
  MASTERNO           NUMBER(10)     NOT NULL    PRIMARY KEY, -- 관리자 번호
  MASTERID           VARCHAR2(30)     NOT NULL, -- 관리자 아이디
  MASTERPASSWD       VARCHAR2(50)     NOT NULL -- 관리자 비밀번호

  */
  
  /** 관리자 번호 */
  private int masterno;
  /** 관리자 아이디 */
  private String masterid = "";
  /** 관리자 패스워드 */
  private String masterpasswd = "";
}