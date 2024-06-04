package dev.mvc.facilityreview;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FacilityreviewVO {
  /** 댓글번호*/
  private int rno;
  /** 관련 글 번호*/
  private int culturefno;
  /** 회원 번호 */
  private int memberno;
  /** 댓글내용 */
  private String reviewcomment;
  /** 등록일 */
  private String rdate;
  
  


}
