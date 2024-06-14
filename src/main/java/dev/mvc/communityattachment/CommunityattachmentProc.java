package dev.mvc.communityattachment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.communityattachment.CommunityattachmentProc")
public class CommunityattachmentProc implements CommunityattachmentProcInter {

  @Autowired
  private CommunityattachmentDAOInter communityattachmentDAO;
  
  public CommunityattachmentProc() {
    
  }
  @Override
  public int create(CommunityattachmentVO communityattachmentVO) {
    int cnt = this.communityattachmentDAO.create(communityattachmentVO);
    return cnt;
  }
  @Override
  public int delete(int cano) {
    int cnt = this.communityattachmentDAO.delete(cano);
    return cnt;
  }
  @Override
  public int create_image(CommunityattachmentVO communityattachmentVO) {
    int cnt = this.communityattachmentDAO.create_image(communityattachmentVO);
    return cnt;
  }
  
  

}
