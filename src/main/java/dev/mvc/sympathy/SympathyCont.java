package dev.mvc.sympathy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.login.LoginCont;
import dev.mvc.login.LoginProcInter;
import dev.mvc.login.LoginVO;
import dev.mvc.master.MasterProcInter;
import dev.mvc.master.MasterVO;
import dev.mvc.memberprofile.Memberprofile;
import dev.mvc.memberprofile.MemberprofileProcInter;
import dev.mvc.memberprofile.MemberprofileVO;
import dev.mvc.notice.Notice;
import dev.mvc.notice.NoticeProcInter;
import dev.mvc.notice.NoticeVO;
import dev.mvc.openapi.OpenAPIController;
import dev.mvc.openapi.OpenAPIDTO;
import dev.mvc.team6_v2sbm3c.MsgCont;
import dev.mvc.tool.OpenAPI;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/sympathy")
@Controller
public class SympathyCont {
  @Autowired
  @Qualifier("dev.mvc.sympathy.SympathyProc")
  private SympathyProcInter sympathyProc;
  
  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc")
  private NoticeProcInter noticeProc;

  @PostMapping(value = "/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody SympathyVO sympathyVO, NoticeVO noticeVO) {
    System.out.println("-> 수신 데이터 : ");

    System.out.println("session : " + session);

    int memberno = (int) session.getAttribute("memberno");
    noticeVO = (NoticeVO) session.getAttribute("noticeVO");
    int noticeno = noticeVO.getNoticeno();
    HashMap<String, Object> map = new HashMap<>();
    map.put("memberno", memberno);
    map.put("noticeno", noticeno);

    int readcnt = this.sympathyProc.read(map);

    JSONObject json = new JSONObject();

    sympathyVO.setMemberno(memberno);
    sympathyVO.setNoticeno(noticeno);

    System.out.println("sympathyVO memberno : " + sympathyVO.getMemberno());
    System.out.println("sympathyVO noticeno : " + sympathyVO.getNoticeno());

    int like_cnt = noticeVO.getLikecnt();
    
    if (readcnt == 0) {
      int cnt = this.sympathyProc.create(sympathyVO);
      like_cnt++;
      System.out.println("like_cnt : " + like_cnt);
      System.out.println("noticeno : " + noticeno);
      noticeVO.setLikecnt(like_cnt);
      this.noticeProc.update_likecnt(noticeVO);
      
      json.put("res", cnt);
      json.put("likecnt", like_cnt);

      return json.toString();
    } else {
      int cnt = this.sympathyProc.delete(map);
      like_cnt--;
      noticeVO.setLikecnt(like_cnt);
      this.noticeProc.update_likecnt(noticeVO);

      json.put("res", cnt);
      json.put("likecnt", like_cnt);

      return json.toString();
    }
  }

  @PostMapping(value = "/read")
  @ResponseBody
  public String read(HttpSession session, @RequestBody SympathyVO sympathyVO) {
    HashMap<String, Object> map = new HashMap<>();
    int memberno=0;
    
    NoticeVO noticeVO = (NoticeVO) session.getAttribute("noticeVO");
    
    int noticeno = noticeVO.getNoticeno();
    map.put("noticeno", noticeno);
    int cnt=0;
    if (session.getAttribute("memberno") != null) {
      memberno = (int) session.getAttribute("memberno");
      map.put("memberno", memberno);
      cnt = this.sympathyProc.read(map);
    } else {
      cnt = this.sympathyProc.read_nt(noticeno);
    }
    
    JSONObject json = new JSONObject();
System.out.println("read noticeno : " + noticeno);
    int likecnt = this.noticeProc.read(noticeno).getLikecnt();
    json.put("likecnt", likecnt);

    if (cnt == 1) {
      json.put("res", cnt);

      return json.toString();
    } else {
      json.put("res", cnt);
      
      return json.toString();
    }
  }

  @GetMapping(value = "/read_all")
  public String read_all(HttpSession session, Model model, @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("memberno") == null) {
      return "member/login";
    }

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("now_page", now_page);

    model.addAttribute("word", word);
    
    int memberno = (int)session.getAttribute("memberno");
    map.put("memberno", memberno);
    
    ArrayList<SympathynoticeVO> list = this.sympathyProc.read_all(memberno);

    model.addAttribute("list", list);
    
    
    int search_count = this.sympathyProc.list_search_count(map);
    String paging = this.sympathyProc.pagingBox(now_page, word, "/sympathy/read_all", search_count, Sympathy.RECORD_PER_PAGE,
        Sympathy.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    model.addAttribute("search_count", search_count);

    int no = search_count - ((now_page - 1) * Sympathy.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    return "notice/read_all";
  }

}
