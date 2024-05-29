package dev.mvc.catecommunity;

import java.util.ArrayList;

public interface CateCommunityProcInter {
  /*
   * 등록
   */
  public int create(CateCommunityVO catecommunityVO);
  
  /*
   * 목록
   */
  public ArrayList<CateCommunityVO>list_all();
  
  public CateCommunityVO read(int ctypeno);
  
  public int update(CateCommunityVO cateCommunityVO);
  
  public int delete(int ctypeno);
}
