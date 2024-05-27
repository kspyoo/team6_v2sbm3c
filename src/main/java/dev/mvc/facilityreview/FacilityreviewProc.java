package dev.mvc.facilityreview;

import java.util.ArrayList;
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
  public ArrayList<FacilityreviewVO> list() {
    ArrayList<FacilityreviewVO> list=this.facilityreviewDAO.list();
    return list;
  }

  @Override
  public ArrayList<FacilityreviewVO> list_by_culturefno(int culturefno) {
    ArrayList<FacilityreviewVO> list = facilityreviewDAO.list_by_culturefno(culturefno);
    String reviewcomment = "";
    
    // 특수 문자 변경
    for (FacilityreviewVO facilityreviewVO:list) {
      reviewcomment = facilityreviewVO.getReviewcomment();
      reviewcomment = Tool.convertChar(reviewcomment);
      facilityreviewVO.setReviewcomment(reviewcomment);
    }
    return list;
  }

  @Override
  public ArrayList<FacilityreviewMemberVO> list_by_culturefno_join(int culturefno) {
    ArrayList<FacilityreviewMemberVO> list = facilityreviewDAO.list_by_culturefno_join(culturefno);
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
  public int checkPasswd(Map<String, Object> map) {
    int cnt = this.facilityreviewDAO.checkPasswd(map);
    return cnt ;
  }

  @Override
  public int delete(int rno) {
    int cnt =this.facilityreviewDAO.delete(rno);
    return cnt;
  }
  

}
