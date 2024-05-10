package dev.mvc.member;

public interface MemberProcInter {
  /**
   * 회원 가입
   * @param memberVO
   * @return 추가한 레코드 갯수
   */
  public int create(MemberVO memberVO);
}
