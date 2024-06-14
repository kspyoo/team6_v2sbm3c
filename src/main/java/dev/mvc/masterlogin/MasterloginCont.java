package dev.mvc.masterlogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.login.LoginMemberVO;
import dev.mvc.master.MasterProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/masterlogin")
@Controller
public class MasterloginCont {
  
  @Autowired
  @Qualifier("dev.mvc.masterlogin.MasterloginProc")
  private MasterloginProcInter masterloginProc;
  
  @Autowired
  @Qualifier("dev.mvc.master.MasterProc")
  private MasterProcInter masterProc;
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 5;
  
  @GetMapping(value="/login_list")
  public String list_search(HttpSession session,Model model,
                      String word,
                      @RequestParam(name = "now_page", defaultValue="1") int now_page) {
    
    word = Tool.checkNull(word).trim();
    ArrayList<LoginMasterVO> list = this.masterloginProc.list_by_search_paging(word,now_page,this.record_per_page);

    model.addAttribute("list",list);
    
    int search_count = this.masterloginProc.list_by_search_count(word);
    System.out.println("search_count in cont.java : " + this.masterloginProc.list_by_search_count(word));
    String paging = this.masterloginProc.pagingBox(now_page, word, "/masterlogin/login_list", search_count, this.record_per_page, this.page_per_block);
    
    model.addAttribute("paging",paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no",search_count);
    model.addAttribute("all_no",no);
    return "masterlogin/login_list";
  }

}
