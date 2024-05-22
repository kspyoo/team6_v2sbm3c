package dev.mvc.culturefile;

public interface CulturefileDAOInter { 
  /**
   * 파일 등록
   * @param culturefileVO
   * @return
   */
  public int create(CulturefileVO culturefileVO);
  /**
   * 파일 정보 수정
   * @param culturefileVO
   * @return
   */
  public int update_file(CulturefileVO culturefileVO);
  
  /**
   * 조회
   * @param culturefno
   * @return
   */
  public CulturefileVO read(int culturefno);

}
