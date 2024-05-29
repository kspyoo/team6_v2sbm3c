package dev.mvc.catecommunity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;



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
    
    
    model.addAttribute("cnt", cnt);
    return "/catecommunity/msg"; // /templates/cate/msg.html
  }
  
  @GetMapping("/list_all")
  public String list_all(Model model) {
    ArrayList<CateCommunityVO>list = this.catecomunityProc.list_all();  
    model.addAttribute("list",list);
  
  return "catecommunity/list_all";
  }
  
  @GetMapping(value = "/read/{ctypeno}")
  public String read(Model model, @PathVariable("ctypeno") Integer ctypeno ) {
    CateCommunityVO cateCommunityVO = this.catecomunityProc.read(ctypeno);
    model.addAttribute("cateCommunityVO",cateCommunityVO);
    
    return "catecommunity/read";
  }
  @GetMapping(value = "/update/{ctypeno}")
  public String update(Model model,
      @PathVariable("ctypeno") Integer ctypeno) {
    CateCommunityVO cateCommunityVO = this.catecomunityProc.read(ctypeno);
    model.addAttribute("cateCommunityVO",cateCommunityVO);
    
    return "catecommunity/update";
  }
  @PostMapping(value = "/update/{ctypeno}")
  public String update_process(Model model, @Valid CateCommunityVO cateCommunityVO,
      @PathVariable("ctypeno") Integer ctypeno) {
    int cnt = this.catecomunityProc.update(cateCommunityVO);
    
    if(cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("title", cateCommunityVO.getTypename());
    }else {
      model.addAttribute("code", "update_fail");
    }
    model.addAttribute("cnt", cnt);
    return "catecommunity/msg";
  }
  @GetMapping(value = "/delete/{ctypeno}")
  public String delete(Model model,@PathVariable("ctypeno") Integer ctypeno) {
    CateCommunityVO cateCommunityVO = this.catecomunityProc.read(ctypeno);
    model.addAttribute("cateCommunityVO",cateCommunityVO);
    
    return "catecommunity/delete";
  }
  
  @PostMapping(value = "/delete/{ctypeno}")
  public String delete_process(Model model, @Valid CateCommunityVO cateCommunityVO,
      @PathVariable("ctypeno") Integer ctypeno) {
    System.out.println("여기인가1?");
    int cnt =this.catecomunityProc.delete(ctypeno);
    model.addAttribute("ctypeno",ctypeno);
    System.out.println("여기인가2?");
    if(cnt == 1 ) {
      model.addAttribute("cdoe", "delete_success");
      model.addAttribute("title", cateCommunityVO.getTypename());
      System.out.println();
    } else {
      model.addAttribute("code", "delete_fail");
      System.out.println("여기인가3?");
    }
    model.addAttribute("cnt", cnt);
    System.out.println("여기인가4?");
    return "community/msg";
    
  }
  
 }
