package dev.mvc.masterlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface MasterloginDAOInter {
  /**
   * 내역추가
   * 
   * @param map
   * @return
   */
  public int create_masterlogin_record(HashMap<String, Object> map);

  /**
   * 회원 전체 목록
   * 
   * @return
   */
  public ArrayList<LoginMasterVO> masterlogin_list(String word);

  /**
   * 회원 전체 목록
   * 
   * @return
   */
  public int masterlogin_list_no(int masterloginno);


  /**
   * 검색 목록
   * 
   * @param map
   * @return
   */
  public ArrayList<LoginMasterVO> list_by_search_paging(Map<String, Object> map);

  /**
   * 카테고리별 검색된 레코드 갯수
   * 
   * @param map
   * @return
   */
  public int list_by_search_count(String word);

 
}
