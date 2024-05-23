package dev.mvc.login;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberDAOInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.memberprofile.MemberprofileProcInter;
import dev.mvc.memberprofile.MemberprofileVO;



@Component("dev.mvc.login.LoginProc")
public class LoginProc implements LoginProcInter{
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.memberprofile.MemberprofileProc")
  private MemberprofileProcInter memberprofileProc;
  
  MemberVO memberVO;
  
  @Autowired
  private LoginDAOInter loginDAO;

  @Override
  public int create_login_record(HashMap<String, Object> map) {
    
    int cnt = this.loginDAO.create_login_record(map);
    return cnt;
  }

  @Override
  public ArrayList<LoginVO> login_list() {
    ArrayList<LoginVO> list = this.loginDAO.login_list();

    for (LoginVO login : list) {
      int memberno = login.getMemberno();
      memberVO = this.memberProc.read(memberno);
      MemberprofileVO memberprofileVO = this.memberprofileProc.read_file(memberno);
      memberVO.setMprofileno(memberprofileVO.getMprofileno());
      login.setName(memberVO.getName());
      login.setId(memberVO.getId());
      login.setMprofileno(memberVO.getMprofileno());
      int index = list.indexOf(login);
      list.set(index, login);
    }
    
    return list;
  }
  
  @Override
  public int login_list_no(int loginno) {
    int memberno = this.loginDAO.login_list_no(loginno);
    
    return memberno;
  }



}
