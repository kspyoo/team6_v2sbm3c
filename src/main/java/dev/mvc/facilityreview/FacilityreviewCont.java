package dev.mvc.facilityreview;

import java.util.ArrayList;



import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.culturefacility.CulturefacilityProc;
import dev.mvc.culturefacility.CulturefacilityVO;
import dev.mvc.member.MemberProc;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class FacilityreviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.facilityreview.FacilityreviewProc") // 이름지정
  private FacilityreviewProcInter facilityreviewProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // 이름지정
  private MemberProc memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.culturefacility.CulturefacilityProc") // 이름지정
  private CulturefacilityProc culturefacilityProc;
  
  public FacilityreviewCont(){
    System.out.println("-> FacilityreviewCont .");
  }
  
  
  // 댓글 작성 페이지 매핑
  @GetMapping("/facilityreview/create/{culturefno}")
  public String create() {
      return "/facilityreview/create"; // templates/create.html
  }
  
  @ResponseBody
  @PostMapping(value = "/facilityreview/create")
  public String create(@RequestBody FacilityreviewVO facilityreviewVO, HttpSession session) {
      Integer memberno = (Integer) session.getAttribute("memberno");
      if (memberno == null) {
          return "{\"cnt\":0, \"msg\":\"로그인이 필요합니다.\"}";
      }

      facilityreviewVO.setMemberno(memberno);
      int cnt = facilityreviewProc.create(facilityreviewVO);

      JSONObject obj = new JSONObject();
      obj.put("cnt", cnt);

      return obj.toString();
  }

  
  @GetMapping(value="/facilityreview/list")
  public String list(HttpSession session, Model model) {
    ArrayList<FacilityreviewVO> list = facilityreviewProc.list();
    model.addAttribute("list", list);
  
    return "facilityreview/list"; // /WEB-INF/views/member/login_ck_form.jsp
  }

  
 
  
///**
// * 
// * @param culturefno
// * @return
// */
//  @ResponseBody
//  @GetMapping(value = "/facilityreview/list_by_culturefno")
//  public String list_by_culturefno(int culturefno) {
//    ArrayList<FacilityreviewVO> list = facilityreviewProc.list_by_culturefno(culturefno);
//    
//    JSONObject obj = new JSONObject();
//    obj.put("list", list);
// 
//    return obj.toString(); 
//
//  }
//  
///**
// * 
// * @param culturefno
// * @return
// */
//  @ResponseBody
//  @GetMapping(value = "/facilityreview/list_by_culturefno_join")
//  public String list_by_culturefno_join(int culturefno) {
//    // String msg="JSON 출력";
//    // return msg;
//    
//    ArrayList<FacilityreviewMemberVO> list = facilityreviewProc.list_by_culturefno_join(culturefno);
//    
//    JSONObject obj = new JSONObject();
//    obj.put("list", list);
// 
//    return obj.toString();     
//  }
//    
 
////http://localhost:9091/contents/read.do
//  /**
//   * 조회
//   * @return
//   */
//  @GetMapping(value="/culturefacility/read")
//  public String read_ajax(HttpServletRequest request,Model model, int culturefno) {
//    // public ModelAndView read(int contentsno, int now_page) {
//    // System.out.println("-> now_page: " + now_page);
//    
// 
//
//    CulturefacilityVO culturefacilityVO  = this.culturefacilityProc.read(culturefno);
//    model.addAttribute("CulturefacilityVO", culturefacilityVO); // request.setAttribute("contentsVO", contentsVO);
//    
//    // 단순 read
//    // mav.setViewName("/contents/read"); // /WEB-INF/views/contents/read.jsp
//    
//    // 쇼핑 기능 추가
//    // mav.setViewName("/contents/read_cookie"); // /WEB-INF/views/contents/read_cookie.jsp
//    
//    // 댓글 기능 추가 
//
//    
//    // -------------------------------------------------------------------------------
//    // 쇼핑 카트 장바구니에 상품 등록전 로그인 폼 출력 관련 쿠기  
//    // -------------------------------------------------------------------------------
//    Cookie[] cookies = request.getCookies();
//    Cookie cookie = null;
//
//    String ck_id = ""; // id 저장
//    String ck_id_save = ""; // id 저장 여부를 체크
//    String ck_passwd = ""; // passwd 저장
//    String ck_passwd_save = ""; // passwd 저장 여부를 체크
//
//    if (cookies != null) {  // Cookie 변수가 있다면
//      for (int i=0; i < cookies.length; i++){
//        cookie = cookies[i]; // 쿠키 객체 추출
//        
//        if (cookie.getName().equals("ck_id")){
//          ck_id = cookie.getValue();                                 // Cookie에 저장된 id
//        }else if(cookie.getName().equals("ck_id_save")){
//          ck_id_save = cookie.getValue();                          // Cookie에 id를 저장 할 것인지의 여부, Y, N
//        }else if (cookie.getName().equals("ck_passwd")){
//          ck_passwd = cookie.getValue();                          // Cookie에 저장된 password
//        }else if(cookie.getName().equals("ck_passwd_save")){
//          ck_passwd_save = cookie.getValue();                  // Cookie에 password를 저장 할 것인지의 여부, Y, N
//        }
//      }
//    }
//    
//    System.out.println("-> ck_id: " + ck_id);
//    
//    model.addAttribute("ck_id", ck_id); 
//    model.addAttribute("ck_id_save", ck_id_save);
//    model.addAttribute("ck_passwd", ck_passwd);
//    model.addAttribute("ck_passwd_save", ck_passwd_save);
//    // -------------------------------------------------------------------------------
//    
//    return "/culturefacility/read_cookie_reply";
//  }
//  
}
  

