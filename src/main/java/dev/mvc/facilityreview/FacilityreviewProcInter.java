package dev.mvc.facilityreview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FacilityreviewProcInter {
  /**
   * 등록
   * @param facilityreviewVO
   * @return
   */
  public int create(FacilityreviewVO facilityreviewVO);
  
  /**
   * 글 조회시 댓글 목록 출력
   * @param culturefno
   * @return
   */
  public List<FacilityreviewMemberVO> list_by_culturefno_join(int culturefno);
  
  
  /**
   * 최신글 500건
   * 
   * @param culturefno
   * @return
   */
  public List<FacilityreviewMemberVO> list_by_culturefno_join_500(int culturefno);

  /**
   * 조회
   * 
   * @param rno
   * @return
   */
  public FacilityreviewVO read(int rno);

  /**
   * 댓글수정
   * 
   * @param facilityreviewVO
   * @return
   */
  public int update(FacilityreviewVO facilityreviewVO);

  /**
   * 삭제
   * 
   * @param rno
   * @return
   */

  public int delete(int rno);

}
