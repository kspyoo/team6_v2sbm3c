package dev.mvc.sympathy;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import dev.mvc.member.MemberVO;
import dev.mvc.notice.NoticeVO;

public interface SympathyDAOInter {
  /**
   * 회원 가입
   * @param memberVO
   * @return 추가한 레코드 갯수
   */
  public int create(SympathyVO sympathyVO);
  
  public int read(HashMap<String, Object> map);
  
  public int read_nt(int noticeno);
  
  public int delete(HashMap<String, Object> map);
  
  public ArrayList<SympathynoticeVO> read_all(int memberno);
  
  public ArrayList<SympathynoticeVO> list_search(HashMap<String, Object> map);
  
  public int list_search_count(HashMap<String,Object> map);
  
  public ArrayList<SympathynoticeVO> paging(HashMap<String, Object> map);
}



