package dev.mvc.communitylike;

import java.util.HashMap;

public interface CommunityLikeDAOInter {

  public int create(HashMap<String, Object>map);
  
  public CommunityLikeVO read(HashMap<String, Object>map);
  
 public int add_update(HashMap<String, Object>map);
  
 public int del_update(HashMap<String, Object>map);
 
 public int rcnt_count(HashMap<String, Object>map);
  public int delete(int rcnt);
}
