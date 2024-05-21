package dev.mvc.culturefacility;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.culturefacility.CulturefacilityVO ;

public interface CulturefacilityDAOInter {

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
  public ArrayList<CulturefacilityVO > list_search_paging(Map<String, Object> map);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);
  
}
