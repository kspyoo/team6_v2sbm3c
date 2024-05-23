package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

public interface MemberDAOInter {
  /**
   * 중복 아이디 검사
   * @param id
   * @return 중복 아이디 갯수, 1: 중복, 0: 중복 없음
   */
  public int checkID(String id);
  
  /**
   * 회원 가입
   * @param memberVO
   * @return 추가한 레코드 갯수
   */
  public int create(MemberVO memberVO);
  
  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public MemberVO readById(String id);
  
  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * 수정 처리
   * @param memberVO
   * @return
   */
  public int update(MemberVO memberVO);
  
  /**
   * memberno로 회원 정보 조회
   * @param memberno
   * @return
   */
  public MemberVO read(int memberno);
  
  /**
   * 회원 삭제 처리
   * @param memberno
   * @return
   */
  public int delete(int memberno);
  
  /**
   * 자식테이블 삭제
   * @param memberno
   * @return
   */
  public int delete_FK(int memberno);
  
  /**
   * 아이디 찾기
   * @param name
   * @param phone
   * @return
   */
  public MemberVO findId(@Param("name") String name, @Param("phone") String phone);
  
  /**
   * 아이디 찾기
   * @param 
   * @return
   */
  public int findIdCheck(HashMap<String, Object> map);
  
  /**
   * 비밀번호 찾기
   * @param name
   * @param phone
   * @return
   */
  public MemberVO findPasswd(@Param("name") String name, @Param("phone") String phone, @Param("id") String id);
  
  /**
   * 비밀번호 찾기
   * @param 
   * @return
   */
  public int findPasswdCheck(HashMap<String, Object> map);
  
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
   * 회원 전체 목록
   * @return
   */
  public ArrayList<MemberVO> list();
  
  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<MemberVO> list_no(int memberno);
  
}



