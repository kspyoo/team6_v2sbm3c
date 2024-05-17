package dev.mvc.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  // 구현 클래스를 교체하기 쉬운 구조 지원
import jakarta.servlet.http.HttpSession; //  Spring Boot 3.0~

public interface MasterProcInter {
  
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
  

}
