package dev.mvc.memberprofile;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

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
  public ArrayList<MemberprofileVO> read_file(int memberno);
  
  /**
   * 자식테이블 삭제
   * @param memberno
   * @return
   */
  public int delete_FK(int memberno);
  
  /**
   * 
   * @param memberno
   * @return
   */
  public int delete_others(@Param("memberno") int memberno, @Param("mprofileno") int mprofileno);
  
  /**
   * 
   * @param file1
   * @param file1saved
   * @param thumbfile
   * @param filesize
   * @param memberno
   * @return
   */
  public int create_other_file(MemberprofileVO memberprofileVO);
}