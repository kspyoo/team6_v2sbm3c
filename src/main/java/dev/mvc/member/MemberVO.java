package dev.mvc.member;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MemberVO {
  /*
    MEMBERNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
    ID                                VARCHAR2(30)     NOT NULL,
    PASSWD                            VARCHAR2(50)     NOT NULL,
    NAME                              VARCHAR2(15)     NOT NULL,
    GENDER                            VARCHAR2(5)    NOT NULL,
    BIRTHDAY                          DATE     NOT NULL,
    PHONE                             VARCHAR2(14)     NOT NULL,
    ADDR_CODE                         VARCHAR2(8)    NOT NULL,
    ADDR_DETAIL                       VARCHAR2(30)     NOT NULL,
    JOINDATE                          DATE     NOT NULL,
    STATUS 
   * */
  
  /** 회원 번호 */
  private int memberno;
  /** 아이디 */
  private String id = "";
  /** 비밀번호 */
  private String passwd = "";
  /** 이름 */
  private String name= "";
  /** 성별 */
  private String gender = "";
  /** 생년월일 */
  private String birthday = "";
  /** 전화번호 */
  private String phone = "";
  /** 우편번호 */
  private String addr_code = "";
  /** 주소 */
  private String addr_main = "";
  /** 상세주소 */
  private String addr_detail = "";
  /** 가입일 */
  private String joindate = "";
  /** 계정 상태
   * 1 : 정상 회원, 2 : 휴면 계정, 3 : 탈퇴 회원
   * */
  private String status = ""; 
  
  
}
