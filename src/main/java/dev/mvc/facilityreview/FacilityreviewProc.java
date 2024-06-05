package dev.mvc.facilityreview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import dev.mvc.tool.Tool;

@Component("dev.mvc.facilityreview.FacilityreviewProc")
public class FacilityreviewProc implements FacilityreviewProcInter {
  
  @Autowired
  private FacilityreviewDAOInter facilityreviewDAO;

  @Override
  public int create(FacilityreviewVO facilityreviewVO) {
   int cnt= this.facilityreviewDAO.create(facilityreviewVO);
    return cnt;
  }
  
  
  @Override
  public List<FacilityreviewMemberVO> list_by_culturefno_join(int culturefno) {
    List<FacilityreviewMemberVO> list = facilityreviewDAO.list_by_culturefno_join(culturefno);
    String reviewcomment = "";
    
    // 특수 문자 변경
    for (FacilityreviewMemberVO facilityreviewMemberVO:list) {
      reviewcomment = facilityreviewMemberVO.getReviewcomment();
      reviewcomment = Tool.convertChar(reviewcomment);
      facilityreviewMemberVO.setReviewcomment(reviewcomment);
    }
    return list;
  }


  @Override
  public List<FacilityreviewMemberVO> list_by_culturefno_join_500(int culturefno) {
    List<FacilityreviewMemberVO> list =this.facilityreviewDAO.list_by_culturefno_join_500(culturefno);
    return list;
  }


  @Override
  public FacilityreviewVO read(int rno) {
    FacilityreviewVO facilityreviewVO =this.facilityreviewDAO.read(rno);
    return facilityreviewVO ;
  }


  @Override
  public int update(FacilityreviewVO facilityreviewVO) {
    int cnt =this.facilityreviewDAO.update(facilityreviewVO);
    return cnt;
  }


  @Override
  public int delete(int rno) {
    int cnt= this.facilityreviewDAO.delete(rno);
    return cnt;
  }
  
  





  

}
