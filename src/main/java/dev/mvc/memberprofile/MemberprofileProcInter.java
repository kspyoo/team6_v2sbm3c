package dev.mvc.memberprofile;

public interface MemberprofileProcInter {
  /**
   * 사진 등록
   * @return
   */
  public int create_file(int memberno);
  
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
  
  /**
   * 자식테이블 삭제
   * @param memberno
   * @return
   */
  public int delete_FK(int memberno);
}
