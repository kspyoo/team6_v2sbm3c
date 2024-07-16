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
  public ArrayList<attachmentVO>list();
  
  public ArrayList<attachmentVO>list_all();
  
  /*
   * 조회
   */
  public attachmentVO read(int communityno);
    
  /*
   * 수정
   */
  public int update(CommunityVO communityVO);
  
  public int delete(int communityno);
  
  public ArrayList<attachmentVO>list_search_paging(HashMap<String,Object>map);
  
  public int list_search_count(String word);
  
  public int vcnt(int communityno);
  
  public int rcnt_add(HashMap<String, Object>map);
  
  public int rcnt_del(HashMap<String, Object>map);
  
}


