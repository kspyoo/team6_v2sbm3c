package dev.mvc.communitylike;

import java.util.ArrayList;
import java.util.HashMap;

public interface CommunityLikeDAOInter {

  public int create(CommunityLikeVO communityLikeVO);
  
  public int read(HashMap<String, Object>map);
  
 public int like_check(HashMap<String, Object>map);
  
 public int like_check_cancel(HashMap<String, Object>map);
 
 public int rcnt_count(HashMap<String, Object>map);
 
 
 
 public CommunityLikeVO read_all(HashMap<String, Object>map);
 
 
 
  public int delete(int rcnt);
}
