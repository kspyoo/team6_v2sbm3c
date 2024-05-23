package dev.mvc.facilityreview;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.member.MemberProc;
import jakarta.servlet.http.HttpSession;

public class FacilityreviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.facilityreview.FacilityreviewProc") // 이름지정
  private FacilityreviewProcInter facilityreviewProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // 이름지정
  private MemberProc memberProc;
  
  public FacilityreviewCont(){
    System.out.println("-> FacilityreviewCont created.");
  }
  
  
  @ResponseBody
  @PostMapping(value = "/facilityreview/create",
                            produces = "text/plain;charset=UTF-8")
  public String create(FacilityreviewVO facilityreviewVO) {
    int cnt = facilityreviewProc.create(facilityreviewVO);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt",cnt);
 
    return obj.toString(); // {"cnt":1}

  }
  
  @GetMapping(value="/facilityreview/list")
  public ModelAndView list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
  
      ArrayList<FacilityreviewVO> list = facilityreviewProc.list();
      
      mav.addObject("list", list);
      mav.setViewName("/facilityreview/list"); // /webapp/reply/list.jsp

      mav.addObject("return_url", "/facilityreview/list"); // 로그인 후 이동할 주소 ★
      mav.setViewName("redirect:/member/login"); // /WEB-INF/views/member/login_ck_form.jsp
    
    
    return mav;
  }

/**
 * 
 * @param culturefno
 * @return
 */
  @ResponseBody
  @GetMapping(value = "/facilityreview/list_by_contentsno",
              produces = "text/plain;charset=UTF-8")
  public String list_by_culturefsno(int culturefno) {
    ArrayList<FacilityreviewVO> list = facilityreviewProc.list_by_culturefno(culturefno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString(); 

  }
  
/**
 * 
 * @param culturefno
 * @return
 */
  @ResponseBody
  @GetMapping(value = "/facilityreview/list_by_contentsno_join",
              produces = "text/plain;charset=UTF-8")
  public String list_by_culturefno_join(int culturefno) {
    // String msg="JSON 출력";
    // return msg;
    
    ArrayList<FacilityreviewMemberVO> list = facilityreviewProc.list_by_culturefno_join(culturefno);
    
    JSONObject obj = new JSONObject();
    obj.put("list", list);
 
    return obj.toString();     
  }
  
}
  

