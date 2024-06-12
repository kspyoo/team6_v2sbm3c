package dev.mvc.community;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public interface CommunityProcInter {

  public int create(CommunityVO communityVO);
  
  public ArrayList<attachmentVO>list();
  
  public ArrayList<attachmentVO>list_all();
  
  public attachmentVO read(int communityno);
  
  public int update(CommunityVO communityVO);
  
  public int delete(int communityno);
  
  public ArrayList<CommunityVO>list_search_paging(String word, int now_page, int record_per_page);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block);   

  
  public int list_search_count(HashMap<String, Object> hashMap);
}
