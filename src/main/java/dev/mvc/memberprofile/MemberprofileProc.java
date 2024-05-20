package dev.mvc.memberprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.memberprofile.MemberprofileProc")
public class MemberprofileProc implements MemberprofileProcInter{
  @Autowired
  private MemberprofileDAOInter memberprofileDAO;

  @Override
  public int update_file(MemberprofileVO memberprofileVO) {
    int cnt = this.memberprofileDAO.update_file(memberprofileVO);
    return cnt;
  }
}
