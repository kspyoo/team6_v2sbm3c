package dev.mvc.member;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  Security security;
  
  public MemberCont() {}
  
  @GetMapping(value="/checkID")
  @ResponseBody
  public String checkID(String id) {
    int cnt = this.memberProc.checkID(id);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    return obj.toString();
  }
  
  /**
   * 회원 가입 폼
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/create")
  public String create_form(Model model, MemberVO memberVO) {
    return "member/create";    
  }
  
  @PostMapping(value="/create")
  public String create_proc(Model model, MemberVO memberVO) {
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());
    
    if (checkID_cnt == 0) {
      memberVO.setStatus("1");
      int cnt = this.memberProc.create(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("name", memberVO.getName());
        model.addAttribute("id", memberVO.getId());
      } else {
        model.addAttribute("code","create_fail");
      }
      model.addAttribute("cnt",cnt);
    } else { // id 중복
       model.addAttribute("code", "create_fail");
       model.addAttribute("cnt",0);
    }
    return "member/msg";
  }
  
  /**
   * 로그아웃
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate();  // 모든 세션 변수 삭제
    return "redirect:/";
  }
  
  /**
   * 로그인
   * @param model
   * @param request 회원번호
   * @return 회원 정보
   */
  @GetMapping(value="/login")
  public String login_form(Model model, HttpServletRequest request) {
    // Cookie
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;
    
    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크
    
    if (cookies != null) { // 쿠키가 있을때
      for (int i=0;i < cookies.length; i++) {
        cookie = cookies[i];
        
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue(); 
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue(); 
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue(); 
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue(); 
        }
      }
    } // Cookie 
    
    model.addAttribute("ck_id", ck_id);
    model.addAttribute("ck_id_save", ck_id_save);
    model.addAttribute("ck_passwd",ck_passwd);
    model.addAttribute("ck_passwd_save",ck_passwd_save);
    
    return "member/login_cookie";
  }
  
  /**
   * 로그인 처리
   * @param session
   * @param request
   * @param response
   * @param model
   * @param id
   * @param passwd
   * @param id_save
   * @param passwd_save
   * @return
   */
  @PostMapping(value="/login")
  public String login_proc(HttpSession session,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model, 
                                     String id, 
                                     String passwd,
                                     @RequestParam(value="id_save", defaultValue = "") String id_save,
                                     @RequestParam(value="passwd_save", defaultValue = "") String passwd_save
                                     ) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));
    
    int cnt = this.memberProc.login(map);
    System.out.println("-> login_proc cnt: " + cnt);
    model.addAttribute("cnt",cnt);
    if (cnt==1) {
      
      MemberVO memberVO = this.memberProc.readById(id);
      session.setAttribute("memberno", memberVO.getMemberno());
      session.setAttribute("id", memberVO.getId());
      session.setAttribute("name", memberVO.getName());
      
      if (id_save.equals("Y")) { // id 저장하는 경우
        Cookie ck_id = new Cookie("ck_id",id);
        ck_id.setPath("/");
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30일
        response.addCookie(ck_id);
      } else { // id 저장안하는 경우
        Cookie ck_id = new Cookie("ck_id","");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id);
      }
      
      // id를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_id_save);
    
    
    if (passwd_save.equals("Y")) { // passwd 저장할 경우
      Cookie ck_passwd = new Cookie("ck_passwd", passwd);
      ck_passwd.setPath("/");
      ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_passwd);
    } else { //passwd 저장하지 않을 경우
      Cookie ck_passwd = new Cookie("ck_passwd", "");
      ck_passwd.setPath("/");
      ck_passwd.setMaxAge(0);
      response.addCookie(ck_passwd);
    }
    // passwd를 저장할지 선택하는  CheckBox 체크 여부
    Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
    ck_passwd_save.setPath("/");
    ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
    response.addCookie(ck_passwd_save);
    
    System.out.println("로그인 성공");
    
    return "redirect:/";
    } else {
      model.addAttribute("code","login_fail");
      System.out.println("로그인 실패");
      return "member/msg";
    } 
    
  }
  
  /**
   * 수정 처리
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/update")
  public String update_proc(Model model, MemberVO memberVO) {
    int cnt = this.memberProc.update(memberVO);
    
    if (cnt ==1) {
      model.addAttribute("code","update_success");
      model.addAttribute("name",memberVO.getName());
      model.addAttribute("id", memberVO.getId());
    } else {
      model.addAttribute("code","update_fail");
    }
    
    model.addAttribute("cnt",cnt);
    return "member/msg";
  }
  
  @GetMapping(value="/read")
  public String read(HttpSession session, Model model, int memberno) {
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO" ,memberVO);
    
    return "member/read";
  }
  

  
  
}
