package dev.mvc.sympathy;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SympathyVO {
/**
 *    sympathyno                          NUMBER(10)    NOT NULL     PRIMARY KEY,
    noticeno                        NUMBER(10)    NOT NULL ,
    memberno                          NUMBER(10)    NOT NULL ,
    sdate                         date    NOT NULL ,
 */
  /** 회원 번호 */
  private int sympathyno;
  /** 아이디 */
  private int noticeno;
  /** 비밀번호 */
  private int memberno;
  /** 이름 */
  private String sdate= "";
}
