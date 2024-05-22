package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
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
  
  @Override
  public int delete(int memberno) {
    int cnt = this.memberDAO.delete(memberno);
    return cnt;
  }

  @Override
  public MemberVO findId(@Param("name") String name, @Param("phone") String phone) {
    MemberVO memberVO = this.memberDAO.findId(name,phone);
    return memberVO;
  }
  
  @Override
  public int findIdCheck(HashMap<String, Object> map) {
    int cnt = this.memberDAO.findIdCheck(map);
    return cnt;
  }
  
  @Override
  public MemberVO findPasswd(@Param("name") String name, @Param("phone") String phone, @Param("id") String id) {
    MemberVO memberVO = this.memberDAO.findId(name,phone);
    return memberVO;
  }
  
  @Override
  public int findPasswdCheck(HashMap<String, Object> map) {
    int cnt = this.memberDAO.findIdCheck(map);
    return cnt;
  }
  
  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_update(map);
    return cnt;
  }
  
  @Override
  public ArrayList<MemberVO> list() {
    ArrayList<MemberVO> list = this.memberDAO.list();
    return list;
  }
  


}
