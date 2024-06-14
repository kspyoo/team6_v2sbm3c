package dev.mvc.reply;

import java.util.List;

public interface ReplyProcInter {

  /**
   * 등록
   * 
   * @param replyVO
   * @return
   */
  public int create(ReplyVO replyVO);

  public List<ReplyMemberVO> list_by_communityno_join_500(int communityno);

  public List<ReplyMemberVO> list_by_communityno_join(int communityno);

  /**
   * 읽기 
   * @param replyno
   * @return
   */
  public ReplyVO read (int replyno);
  
  public int update(ReplyVO replyVO);
  public int delete(int replyno);
}
