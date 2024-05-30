package dev.mvc.catecommunity;

import java.util.ArrayList;

public interface CateCommunityDAOInter {
  
  public int create(CateCommunityVO catecommunityVO);
  
  
  public ArrayList<CateCommunityVO>list();
  
  public CateCommunityVO read(int ctypeno);
  
  public int update(CateCommunityVO cateCommunityVO);
  
  public int delete(int ctypeno);
}
