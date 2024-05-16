package dev.mvc.culturefacility;

import java.util.ArrayList;
import java.util.HashMap;  // class
import java.util.List;
import java.util.Map;

import dev.mvc.culturefacility.CulturefacilityVO;

public interface CulturefacilityDAOInter {
 

  /**
   * cultureno로 문화시설 조회
   * @param cultureno
   * @return
   */
  public CulturefacilityVO read(int cultureno);
  
   /**
    * 문화시설 등록
    * @param culturefacilityVO
    * @return
    */
   public int create(CulturefacilityVO culturefacilityVO);
  /**
   * 문화시설 전체 목록
   * @return
   */
  public ArrayList<CulturefacilityVO> list();
  
  /**
   * 수정 처리
   * @param culturefacilityVO
   * @return
   */
  public int update(CulturefacilityVO culturefacilityVO);

  /**
   * 문화시설정보 삭제 처리
   * @param cultureno
   * @return
   */
  public int delete(int cultureno);
}
