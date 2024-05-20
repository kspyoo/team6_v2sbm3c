package dev.mvc.community;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.catecommunity.CateCommunityVO;

@Service("dev.mvc.community.CommunityProc")
public class CommunityProc implements CommunityProcInter {

  @Autowired
  private CommunityDAOInter communityDAO;
  
  public CommunityProc() {
    
  }

  @Override
  public int create(CommunityVO communityVO) {
    int cnt = this.communityDAO.create(communityVO);
    return cnt;
  }

  @Override
  public ArrayList<CommunityVO> list_all() {
    ArrayList<CommunityVO>list = this.communityDAO.list();
    return list;
  }
}
