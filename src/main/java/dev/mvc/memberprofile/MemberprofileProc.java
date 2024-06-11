package dev.mvc.memberprofile;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
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
  public ArrayList<MemberprofileVO> read_file(int memberno) {
    ArrayList<MemberprofileVO> list = this.memberprofileDAO.read_file(memberno);
    return list;
  }
  
  @Override
  public int delete_FK(int memberno) {
    int cnt = this.memberprofileDAO.delete_FK(memberno);
    return cnt;
  }
  
  @Override
  public int delete_others(@Param("memberno") int memberno, @Param("mprofileno") int mprofileno) {
    int cnt=this.memberprofileDAO.delete_others(memberno, mprofileno);
    return cnt;
  }

  @Override
  public int create_other_file(MemberprofileVO memberprofileVO) {
    int cnt = this.memberprofileDAO.create_other_file(memberprofileVO);
    return cnt;
  }


}