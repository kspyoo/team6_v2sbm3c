package dev.mvc.communitylike;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("dev.mvc.communitylike.CommunityLikeProc")
public class CommunityLikeProc implements CommunityLikeProcInter {

  @Autowired
  private CommunityLikeDAOInter communityLikeDAO;
  
  public CommunityLikeProc() {
    
  }
  @Override
  public int create(HashMap<String, Object>map) {
    int cnt = this.communityLikeDAO.create(map);
    return cnt;
  }
  
  @Override
  public int delete(int rcnt) {
    int cnt =this.communityLikeDAO.delete(rcnt);
    return cnt;
  }
  @Override
  public CommunityLikeVO read(HashMap<String, Object>map) {
    CommunityLikeVO communityLikeVO = this.communityLikeDAO.read(map);
    return communityLikeVO;
  }
  @Override
  public int add_update(HashMap<String, Object> map) {
    int cnt = this.communityLikeDAO.add_update(map);
    return cnt;
  }
  @Override
  public int del_update(HashMap<String, Object> map) {
    int cnt = this.communityLikeDAO.del_update(map);
    return cnt;
  }
  @Override
  public int rcnt_count(HashMap<String, Object> map) {
    int cnt =this.communityLikeDAO.rcnt_count(map);
    return cnt;
  }

}
