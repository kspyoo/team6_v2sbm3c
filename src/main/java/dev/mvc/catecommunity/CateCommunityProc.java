package dev.mvc.catecommunity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service("dev.mvc.catecommunity.CateCommunityProc")
public class CateCommunityProc implements CateCommunityProcInter {
  
  @Autowired
  private CateCommunityDAOInter catecommunityDAO;
  
  public CateCommunityProc() {
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public int create(CateCommunityVO catecommunityVO) {
    int cnt = this.catecommunityDAO.create(catecommunityVO);
    return cnt;
  }

  @Override
  public ArrayList<CateCommunityVO> list_all() {
    ArrayList<CateCommunityVO>list = this.catecommunityDAO.list();
    return list;
  }

  @Override
  public CateCommunityVO read(int ctypeno) {
    CateCommunityVO cateCommunityVO =this.catecommunityDAO.read(ctypeno);
    return cateCommunityVO;
  }

  @Override
  public int update(CateCommunityVO cateCommunityVO) {
    int cnt = this.catecommunityDAO.update(cateCommunityVO);
    return cnt;
  }

  @Override
  public int delete(int ctypeno) {
    int cnt = this.catecommunityDAO.delete(ctypeno);
    return cnt;
  }

}
