package dev.mvc.memberprofile;

public interface MemberprofileProcInter {
  /**
   * 사진 등록
   * @return
   */
  public int create_file(MemberprofileVO memberprofileVO);
  
  /**
   * 파일 수정
   * @return
   */
  public int update_file(MemberprofileVO memberprofileVO);
  
  /**
   * 파일 조회
   * @return
   */
  public MemberprofileVO read_file(int memberno);
}
