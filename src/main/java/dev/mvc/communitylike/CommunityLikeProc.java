package dev.mvc.communitylike;

import java.util.ArrayList;
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
  public int create(CommunityLikeVO communityLikeVO) {
    int cnt = this.communityLikeDAO.create(communityLikeVO);
    return cnt;
  }
  
  @Override
  public int delete(int rcntno) {
    int cnt =this.communityLikeDAO.delete(rcntno);
    return cnt;
  }
  @Override
  public int read(HashMap<String, Object>map) {
    int cnt = this.communityLikeDAO.read(map);
    return cnt;
  }
  @Override
  public int like_check(HashMap<String, Object> map) {
    int cnt = this.communityLikeDAO.like_check(map);
    return cnt;
  }
  @Override
  public int like_check_cancel(HashMap<String, Object> map) {
    int cnt = this.communityLikeDAO.like_check_cancel(map);
    return cnt;
  }
  @Override
  public int rcnt_count(HashMap<String, Object> map) {
    int cnt =this.communityLikeDAO.rcnt_count(map);
    return cnt;
  }
  @Override
  public CommunityLikeVO read_all(HashMap<String, Object> map) {
    CommunityLikeVO communityLikeVO = this.communityLikeDAO.read_all(map);
    return communityLikeVO;
  }
  
  
  
  

}
