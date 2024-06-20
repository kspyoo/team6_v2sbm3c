package dev.mvc.noticeimg;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

public interface NoticeimgDAOInter {
  /**
   * 사진 등록
   * @return
   */
  public int create_file(NoticeimgVO noticeimgVO);
  
  /**
   * 사진 등록
   * @return
   */
  public int create_null(int noticeno);
  
  /**
   * 사진 조회
   * @return
   */
  public ArrayList<NoticeimgVO> read_file(int noticeno);
  
  /**
   * 사진 등록
   * @return
   */
  public int create_others(NoticeimgVO noticeimgVO);
  
  public int delete_one(HashMap<String, Object> map);
}