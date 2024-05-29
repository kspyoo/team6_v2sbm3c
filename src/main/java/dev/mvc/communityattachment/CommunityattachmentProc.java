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

}
