package dev.mvc.community;

import java.util.ArrayList;

public interface CommunityProcInter {

  public int create(CommunityVO communityVO);
  
  public ArrayList<CommunityVO>list_all();
}
