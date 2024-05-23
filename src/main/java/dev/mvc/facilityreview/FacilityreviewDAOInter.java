package dev.mvc.facilityreview;

import java.util.ArrayList;
import java.util.Map;

public interface FacilityreviewDAOInter {
  
  public int create(FacilityreviewVO facilityreviewVO);
  
  public ArrayList<FacilityreviewVO> list();
  
  public ArrayList<FacilityreviewVO> list_by_culturefno(int culturefno);
  
  public ArrayList<FacilityreviewMemberVO> list_by_culturefno_join(int culturefno);
  
  public int checkPasswd(Map<String, Object> map);

  public int delete(int rno);


}
