package dev.mvc.member;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;


@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {
  
  @Autowired
  private MemberDAOInter memberDAO;
  
  @Autowired
  Security security;
  
  public MemberProc() {
    
  }
  
  @Override
  public int checkID(String id) {
    int cnt = this.memberDAO.checkID(id);
    return cnt;
  }
  
  @Override
  public int create(MemberVO memberVO) {
    String passwd = memberVO.getPasswd();
    
    String passwd_encoded = this.security.aesEncode(passwd);
    memberVO.setPasswd(passwd_encoded);
    
    int cnt = this.memberDAO.create(memberVO);
    return cnt;
  }
  
  @Override
  public MemberVO readById(String id) {
    MemberVO memberVO = this.memberDAO.readById(id);
    return memberVO;
  }
  
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.memberDAO.login(map);
    return cnt;
  }
  
  @Override
  public int update(MemberVO memberVO) {
    int cnt = this.memberDAO.update(memberVO);
    return cnt;
  }
  
  @Override
  public MemberVO read(int memberno) {
    MemberVO memberVO = this.memberDAO.read(memberno);
    return memberVO;
  }

}
