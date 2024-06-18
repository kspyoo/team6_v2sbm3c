package dev.mvc.noticeimg;

import java.util.ArrayList;

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
  public ArrayList<NoticeimgVO> read_file(int noticeno) {
    ArrayList<NoticeimgVO> list = this.noticeimgDAO.read_file(noticeno);
    return list;
  }

  


}