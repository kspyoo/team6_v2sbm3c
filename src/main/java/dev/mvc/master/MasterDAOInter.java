package dev.mvc.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.culturefacility.CulturefacilityVO;



public interface MasterDAOInter {
  
  /**
   * 중복 아이디 검사
   * @param id
   * @return 중복 아이디 갯수
   */
  public int checkID(String id);
  
  /**
   * 관리자 가입
   * @param masterVO
   * @return 추가한 레코드 갯수
   */
  public int create(MasterVO masterVO);

  /**
   * 관리자 전체 목록
   * @return
   */
  public ArrayList<MasterVO > list();

  /**
   * masterno로 관리자 정보 조회
   * @param masterno
   * @return
   */
  public MasterVO read(int masterno);
  /**
   * 관리자 아이디로 관리자정보 조회
   * @param id
   * @return
   */
  public MasterVO readById(String masterid);
  
  /**
   * 수정 처리
   * @param masterVO
   * @return
   */
  public int update(MasterVO masterVO);
  
  /**
   * 관리자 삭제 처리
   * @param memberno
   * @return
   */
  public int delete(int masterno);

  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  public int passwd_check(HashMap<String, Object> map);
  
  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  public int passwd_update(HashMap<String, Object> map);
  
  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.mvc.cate.CateVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<MasterVO > list_search(String word);    
      
  /**
   * 검색목록 페이징
   * select id="list_search_paging" resultType="dev.mvc.cate.CateVO" parameterType="Map"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<MasterVO > list_search_paging(Map<String, Object> map);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);
  
 
    
  }


