package dev.mvc.catecommunity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping("/catecommunity")
@Controller
public class CateCommunityCont {
  @Autowired
  @Qualifier("dev.mvc.catecommunity.CateCommunityProc")
  private CateCommunityProcInter catecomunityProc;

  public CateCommunityCont() {
    System.out.println("-> catecommunitycont created");
  }

  @GetMapping("/create")
  public String create(CateCommunityVO cateCommunityVO) {
    return "catecommunity/create";
  }
  
  @PostMapping("/create")
  public String create_process(CateCommunityVO cateCommunityVO, Model model, BindingResult bindingResult) {
    System.out.println("이동됨");
    
    if (bindingResult.hasErrors()) {
      return "/catecommunity/create";
    }
    
    System.out.println("등록");
    int cnt=this.catecomunityProc.create(cateCommunityVO);
    System.out.println("등록완료");
    
    
    if (cnt == 1) {
      model.addAttribute("code", "create_success");
      model.addAttribute("title", cateCommunityVO.getTypename());
    } else {
      model.addAttribute("code", "create_fail");
    }
    
    System.out.println("넘어가기전");
    model.addAttribute("cnt", cnt);
    return "/catecommunity/msg"; // /templates/cate/msg.html
  }
  
  @GetMapping("/list_all")
  public String list_all(Model model) {
    ArrayList<CateCommunityVO>list = this.catecomunityProc.list_all();  
    model.addAttribute("list",list);
  
  return "catecommunity/list_all";
  }
 }
