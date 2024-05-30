package dev.mvc.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.member.MemberVO;

public interface LoginProcInter {
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
  public ArrayList<LoginMemberVO> login_list(String word);
  
  /**
   * 회원 전체 목록
   * @return
   */
  public int login_list_no(int loginno);
  
  /**
   * @param loginVO
   * @return
   */
  public int update_null(int memberno);
  
  /**
   * 검색 목록
   * @param map
   * @return
   */
  public ArrayList<LoginMemberVO> list_by_search_paging(String word, int now_page, int record_per_page);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int list_by_search_count(String word);

  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_block);
  
  public LoginVO findnull();
}
