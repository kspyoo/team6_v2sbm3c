package dev.mvc.catecommunity;

import java.util.ArrayList;

public interface CateCommunityDAOInter {
  
  public int create(CateCommunityVO catecommunityVO);
  
  
  public ArrayList<CateCommunityVO>list();
}
