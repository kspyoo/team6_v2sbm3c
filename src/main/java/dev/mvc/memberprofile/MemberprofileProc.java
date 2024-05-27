package dev.mvc.memberprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.memberprofile.MemberprofileProc")
public class MemberprofileProc implements MemberprofileProcInter{
  @Autowired
  private MemberprofileDAOInter memberprofileDAO;

  @Override
  public int create_file(int memberno) {
    int cnt = this.memberprofileDAO.create_file(memberno);
    return cnt;
  }

  @Override
  public int update_file(MemberprofileVO memberprofileVO) {
    int cnt = this.memberprofileDAO.update_file(memberprofileVO);
    return cnt;
  }
  @Override
  public MemberprofileVO read_file(int memberno) {
    MemberprofileVO memberprofileVO = this.memberprofileDAO.read_file(memberno);
    return memberprofileVO;
  }
  
  @Override
  public int delete_FK(int memberno) {
    int cnt = this.memberprofileDAO.delete_FK(memberno);
    return cnt;
  }


}
