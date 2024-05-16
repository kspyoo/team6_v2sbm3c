package dev.mvc.culturefacility;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import dev.mvc.culturefacility.CulturefacilityVO;
import jakarta.validation.Valid;

@Controller
public class CulturefacilityCont {
  @Autowired
  @Qualifier("dev.mvc.Culturefacility.CulturefacilityProc")
  private CulturefacilityProcInter CulturefacilityProc;

  public CulturefacilityCont() {
    System.out.println("-> CulturefacilityCont created.");
  }

 /**
  * 문화시설 등록
  * @param culturefacilityVO
  * @return
  */
  @GetMapping(value = "/create") // http://localhost:1521/culturefacility/create
  public String create(CulturefacilityVO culturefacilityVO) {
    return "/culturefacility/create";
  }

  /**
   * 문화시설 등록 처리
   * @param model
   * @return
   */
  @PostMapping(value = "/create")
  public String create_process(Model model,@Valid CulturefacilityVO culturefacilityVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/culturefacility/create";
    }
    int cnt=this.CulturefacilityProc.create(culturefacilityVO);
    if (cnt == 1) {
      model.addAttribute("code", "create_success");
      model.addAttribute("cname", culturefacilityVO.getCname());
    } else {
      model.addAttribute("code", "create_fail");
    }
    
    model.addAttribute("cnt", cnt);
    return "/culturefacility/msg"; // /templates/cate/msg.html
  }
    
   
  

  /**
   * 목록
   * 
   * @param model
   * @return
   */
  @GetMapping(value = "/list_all")
  public String list_all(Model model) {
    ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_all();
    model.addAttribute("list", list);

    return "/culturefacility/list_all";// /Culturefacility/list_all.html
  }
  
  /**
   * 문화시설 조회
   * @param model
   * @param cultureno
   * @return
   */
  @GetMapping(value = "/read/{cultureno}")
  public String read(Model model,
                     @PathVariable("cultureno")Integer cultureno) {
       
    CulturefacilityVO culturefacilityVO =this.CulturefacilityProc.read(cultureno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    return "/culturefacility/read";
  }
  
  /**
   * 문화시설 수정
   * @param model
   * @param cultureno
   * @return
   */
  @GetMapping(value = "/update/{cultureno}")
  public String update(Model model,
                     @PathVariable("cultureno")Integer cultureno) {
       
    CulturefacilityVO culturefacilityVO =this.CulturefacilityProc.read(cultureno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    return "/culturefacility/update";
  }
  /**
   * 문화시설 수정 처리
   * @param model
   * @return
   */
  @PostMapping(value = "/update")
  public String update_process(Model model,@Valid CulturefacilityVO culturefacilityVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/culturefacility/create";
    }
    int cnt=this.CulturefacilityProc.update(culturefacilityVO);
    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("cname", culturefacilityVO.getCname());
    } else {
      model.addAttribute("code", "update_fail");
    }
    
    model.addAttribute("cnt", cnt);
    return "/culturefacility/msg"; 
  }
  
  /**
   * 문화시설 삭제 폼
   * @param model
   * @param cultureno
   * @return
   */
  @GetMapping(value = "/delete/{cultureno}")
  public String delete(Model model,
                     @PathVariable("cultureno")Integer cultureno) {
       
    CulturefacilityVO culturefacilityVO =this.CulturefacilityProc.read(cultureno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    return "/culturefacility/delete";
  }
  
  /**
   * 문화시설 삭제 처리
   * @param model
   * @return
   */
  @PostMapping(value = "/delete")
  public String delete_process(Model model,Integer cultureno) {
    CulturefacilityVO culturefacilityVO =this.CulturefacilityProc.read(cultureno);
    model.addAttribute("culturefacilityVO",culturefacilityVO );
    
   int cnt=this.CulturefacilityProc.delete(cultureno);
    if (cnt == 1) {
      model.addAttribute("code", "delete_success");
      model.addAttribute("cname", culturefacilityVO.getCname());
    } else {
      model.addAttribute("code", "delete_fail");
    }
    
    model.addAttribute("cnt", cnt);
    return "/culturefacility/msg"; 
  }
  
  
  
}
