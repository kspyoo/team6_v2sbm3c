package dev.mvc.login;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.member.MemberVO;

public interface LoginDAOInter {
  /**
   * 내역 추가
   * @param loginVO
   * @return
   */
  public int create_login_record(HashMap<String, Object> map);
  
  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<LoginVO> login_list();
  
  /**
   * 회원 전체 목록
   * @return
   */
  public int login_list_no(int loginno);
  
  /**
   * @return
   */
  public int update_null(int memberno);
}
