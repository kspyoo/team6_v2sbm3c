package dev.mvc.community;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.catecommunity.CateCommunityVO;
import jakarta.validation.Valid;


@RequestMapping("/community")
@Controller
public class CommunityCont {
  @Autowired
  @Qualifier("dev.mvc.community.CommunityProc")
  private CommunityProcInter comunityProc;
  
  
  public CommunityCont() {
    System.out.println("-> communitycont created");
  }
  
  @GetMapping(value = "/create") // http://localhost:1521/culturefacility/create
  public String create(CommunityVO communityVO) {
    return "/community/create";
  }

  @PostMapping(value = "/create")
  public String create_process(Model model,@Valid CommunityVO communityVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/community/create";
    }
    int cnt=this.comunityProc.create(communityVO);
    if (cnt == 1) {
      model.addAttribute("code", "create_success");
      model.addAttribute("title", communityVO.getTitle());
    } else {
      model.addAttribute("code", "create_fail");
    }
    
    model.addAttribute("cnt", cnt);
    return "/community/msg"; // /templates/cate/msg.html
  }
    
  @GetMapping("/list_all")
  public String list_all(Model model) {
    ArrayList<CommunityVO>list = this.comunityProc.list_all();  
    model.addAttribute("list",list);
  
  return "community/list_all";

  }
}
