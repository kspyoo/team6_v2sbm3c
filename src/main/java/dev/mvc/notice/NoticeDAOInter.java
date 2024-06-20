package dev.mvc.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.member.MemberVO;

public interface NoticeDAOInter {
  /**
   * 목록 조회
   * @return
   */
  public ArrayList<NoticeVO> list_all();
  
  /**
   * 등록, 추상 메소드
   * @param contentsVO
   * @return
   */
  public int create(NoticeVO noticeVO);
  
  /**
   * 조회
   * @param noticeno
   * @return
   */
  public NoticeVO read(int noticeno);
  
  /**
   * 수정
   * @param noticeVO
   * @return
   */
  public int update(NoticeVO noticeVO);
  
  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 삭제
   * @param noticeno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int noticeno);
  
  public ArrayList<NoticeVO> list_search(HashMap<String, Object> map);
  
  public int list_search_count(HashMap<String,Object> map);
  
  public ArrayList<NoticeVO> paging(HashMap<String, Object> map);
  
  /**
   * 수정
   * @param noticeVO
   * @return
   */
  public int update_likecnt(NoticeVO noticeVO);
  
  
}
