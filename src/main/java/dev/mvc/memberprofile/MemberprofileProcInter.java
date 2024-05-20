package dev.mvc.memberprofile;

public interface MemberprofileProcInter {
  /**
   * 파일 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(MemberprofileVO memberprofileVO);
}
