package dev.mvc.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CommunityDAOInter {
/*
 * 등록
 */
  public int create(CommunityVO communityVO);
  
  /*
   * 목록
   */
  public ArrayList<CommunityVO>list();
  
  /*
   * 조회
   */
  public CommunityVO read(int communityno);
    
  /*
   * 수정
   */
  public int update(CommunityVO communityVO);
  
  public int delete(int communityno);
  
  public ArrayList<CommunityVO>list_search_paging(Map<String,Object>map);
  
  public int list_search_count(HashMap<String, Object> hashMap);
}
