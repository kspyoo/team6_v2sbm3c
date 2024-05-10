package dev.mvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {
  
  @Autowired
  private MemberDAOInter memberDAO;
  
  public MemberProc() {
    
  }
  
  @Override
  public int create(MemberVO memberVO) {
    String passwd = memberVO.getPasswd();
    
    int cnt = this.memberDAO.create(memberVO);
    return cnt;
  }

}
