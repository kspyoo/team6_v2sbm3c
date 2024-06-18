package dev.mvc.facilityreview;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FacilityreviewVO {
  /**아이디(이메일)*/
  private String id = "";
  /**회원 이름*/
  private String name = "";
  /** 댓글번호*/
  private int rno;
  /** 관련 글 번호*/
  private int culturefno;
  /** 회원 번호 */
  private int memberno;
  /** 관리자 번호 */
  private int masterno;
  /** 댓글내용 */
  private String reviewcomment;
  /** 등록일 */
  private String rdate;
  
  


}
