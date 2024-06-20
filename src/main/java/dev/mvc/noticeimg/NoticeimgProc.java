package dev.mvc.noticeimg;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.noticeimg.NoticeimgProc")
public class NoticeimgProc implements NoticeimgProcInter{
  @Autowired
  private NoticeimgDAOInter noticeimgDAO;

  @Override
  public int create_file(NoticeimgVO noticeimgVO) {
    int cnt = this.noticeimgDAO.create_file(noticeimgVO);
    return cnt;
  }
  
  @Override
  public int create_null(int noticeno) {
    int cnt = this.noticeimgDAO.create_null(noticeno);
    return cnt;
  }

  @Override
  public ArrayList<NoticeimgVO> read_file(int noticeno) {
    ArrayList<NoticeimgVO> list = this.noticeimgDAO.read_file(noticeno);
    return list;
  }

  @Override
  public int create_others(NoticeimgVO noticeimgVO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete_one(HashMap<String, Object> map) {
    int cnt = this.noticeimgDAO.delete_one(map);
    return cnt;
  }

  


}