package dev.mvc.reply;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.member.MemberProcInter;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/reply")
@Controller
public class ReplyCont {
  public ReplyCont() {
    System.out.println("-> ReplyCont created");
  }
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc")
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProcInter;

  
  @PostMapping(value = "/create")
  @ResponseBody
  public String create(@RequestBody ReplyVO replyVO, HttpSession session) {
    System.out.println("-> 수신 데이터:" + replyVO.toString());
    
    int memberno =(int) session.getAttribute("memberno"); // 보안성 향상
    replyVO.setMemberno(memberno);
    
    int cnt = this.replyProc.create(replyVO);
    
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    
    return json.toString();
  }
  
  @GetMapping(value = "/list_by_communityno_join")
  @ResponseBody
  public String list_by_communityno_join(int communityno) {
    List<ReplyMemberVO> list = replyProc.list_by_communityno_join_500(communityno);

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    // System.out.println("-> obj.toString(): " + obj.toString());

    return obj.toString();
  }

  /**
   * 조회
   * 
   * @param contentsno
   * @return
   */
  @GetMapping(value = "/read", produces = "application/json")
  @ResponseBody
  public String read(int replyno) {
    ReplyVO replyVO = this.replyProc.read(replyno);
    // -> obj.toString(): {"res":"ReplyVO(replyno=31, contentsno=2, memberno=2, content=금요일, rdate=2024-05-31 17:24:57)"}
    JSONObject row = new JSONObject();
    
    row.put("replyno", replyVO.getReplyno());
    row.put("communityno", replyVO.getCommunityno());
    row.put("memberno", replyVO.getMemberno());
    row.put("content", replyVO.getContent());
    row.put("rdate", replyVO.getRdate());    
    
    JSONObject obj = new JSONObject();
    obj.put("res", row);

    // System.out.println("-> obj.toString(): " + obj.toString());
    return obj.toString();
  }
  /**
   * 수정 처리 
   * @param replyVO
   * @return
   */
  @PostMapping(value = "/update")
  @ResponseBody
  public String update(HttpSession session, @RequestBody ReplyVO replyVO) {
    System.out.println("-> 수정할 수신 데이터:" + replyVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상
    
    int cnt = 0;
    if (memberno == replyVO.getMemberno()) { // 회원 자신이 쓴글만 수정 가능
      cnt = this.replyProc.update(replyVO);
    }
    
    JSONObject json = new JSONObject();
    json.put("res", cnt);  // 1: 성공, 0: 실패

    return json.toString();
  }
  
  /**
   * 삭제 처리 
   * @param replyVO
   * @return
   */
  @PostMapping(value = "/delete")
  @ResponseBody
  public String delete(HttpSession session, @RequestBody ReplyVO replyVO) {
    // System.out.println("-> 삭제할 수신 데이터:" + replyVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상
    
    int cnt = 0;
    if (memberno == replyVO.getMemberno()) { // 회원 자신이 쓴글만 수정 가능
      cnt = this.replyProc.delete(replyVO.getReplyno());
    }
    
    JSONObject json = new JSONObject();
    json.put("res", cnt);  // 1: 성공, 0: 실패

    return json.toString();
  }

}
