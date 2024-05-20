package dev.mvc.community;

import java.util.ArrayList;

public interface CommunityDAOInter {

  public int create(CommunityVO communityVO);
  
  public ArrayList<CommunityVO>list();
}
