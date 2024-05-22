package dev.mvc.culturefacility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  // 구현 클래스를 교체하기 쉬운 구조 지원
import jakarta.servlet.http.HttpSession; //  Spring Boot 3.0~

public interface CulturefacilityProcInter {
  
  /**
   * 문화시설 등록
   * 
   * @param culturefacilityVO
   * @return
   */
  public int create(CulturefacilityVO culturefacilityVO);
  
  /**
   * 문화시설 전체 목록
   * 
   * @return
   */
  public ArrayList<CulturefacilityVO> list_all();

  /**
   * cultureno로 문화시설 조회
   * 
   * @param culturefno
   * @return
   */
  public CulturefacilityVO read(int culturefno);


  /**
   * 수정 처리
   * 
   * @param culturefacilityVO
   * @return
   */
  public int update(CulturefacilityVO culturefacilityVO);

  /**
   * 문화시설정보 삭제 처리
   * 
   * @param culturefno
   * @return
   */
  public int delete(int culturefno);
  
  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.mvc.cate.CateVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<CulturefacilityVO > list_search(String word);    
      
  /**
   * 검색목록 페이징
   * select id="list_search_paging" resultType="dev.mvc.cate.CateVO" parameterType="Map"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<CulturefacilityVO > list_search_paging(String word, int now_page, int record_per_page);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);

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

  

}