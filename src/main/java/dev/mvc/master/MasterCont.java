package dev.mvc.master;

import java.util.ArrayList;
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

import dev.mvc.master.MasterVO;
import dev.mvc.member.MemberVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/master")
@Controller
public class MasterCont {
  @Autowired
  @Qualifier("dev.mvc.master.MasterProc")
  private MasterProcInter masterProc;
  
  @Autowired
  Security security;

  public MasterCont() {
    System.out.println("->MasterCont created");
  }

  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String masterid) {
    int cnt = this.masterProc.checkID(masterid);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();
  }

  /**
   * 회원 가입 폼
   * 
   * @param model
   * @param masterVO
   * @return
   */
  @GetMapping(value = "/create") // http://localhost:1521/master/create
  public String create_form(Model model, MasterVO masterVO) {
    return "master/create"; // /template/master/create.html
  }

  @PostMapping(value = "/create")
  public String create_proc(Model model, MasterVO masterVO) {
    int checkID_cnt = this.masterProc.checkID(masterVO.getMasterid());

    if (checkID_cnt == 0) {
      int cnt = this.masterProc.create(masterVO);

      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("masterid", masterVO.getMasterid());
      } else {
        model.addAttribute("code", "create_fail");
      }
      model.addAttribute("cnt", cnt);
    } else { // 아이디가 중복
      model.addAttribute("code", "duplicate_fail");
      model.addAttribute("cnt", 0);
    }

    return "master/msg";
  }
  /**
   * 목록
   * @param model
   * @return
   */
  @GetMapping(value = "/list")
  public String list(Model model) {
    ArrayList<MasterVO> list = this.masterProc.list();

    model.addAttribute("list", list);

    return "master/list";
  }
  
  
  /**
   * 조회
   * @param session
   * @param model
   * @param masterno
   * @return
   */
  @GetMapping(value="/read")
  public String read(HttpSession session, Model model, int masterno) {

      
      MasterVO masterVO = this.masterProc.read(masterno);
      model.addAttribute("masterVO", masterVO);
      
      return "master/read";  // templates/member/myPetInfo.html
            
  }
  
 
  
  

  /**
   * 관리자 삭제
   * @param model
   * @param masterno
   * @return
   */
  @GetMapping(value = "/delete")
  public String delete(Model model, int masterno) {
    System.out.println("->delete masterno:" + masterno);

    MasterVO masterVO = this.masterProc.read(masterno);
    model.addAttribute("masterVO", masterVO);

    return "master/delete"; // templates/member/delete.html
  }

 /**
  * Delete process
  * @param model
  * @param masterno 삭제할 관리자 번호
  * @return
  */
  @PostMapping(value = "/delete")
  public String delete_process(Model model, Integer masterno) {
    int cnt = this.masterProc.delete(masterno);

    if (cnt == 1) {
      return "redirect:/master/list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "master/msg"; // /templates/master/msg
    }
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
  
  // -------------------------------------------
  // Cookie 사용 로그인 관련 코드 시작
  // -------------------------------------------

  /**
   * login 로그인
   * 
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value = "/login")
  public String login_form(Model model, HttpServletRequest request) {
    // cookie 관련코드-------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_masterid = ""; // id 저장
    String ck_masterid_save = ""; // id 저장 여부를 체크
    String ck_masterpasswd = ""; // passwd 저장
    String ck_masterpasswd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) { // 쿠키가 존재한다면
      for (int i = 0; i < cookies.length; i++) {
        cookie = cookies[i]; // 쿠키 객체 추출

        if (cookie.getName().equals("ck_masterid")) {
          ck_masterid = cookie.getValue();
        } else if (cookie.getName().equals("ck_masterid_save")) {
          ck_masterid_save = cookie.getValue(); // Y, N
        } else if (cookie.getName().equals("ck_masterpasswd")) {
          ck_masterpasswd = cookie.getValue(); // 1234
        } else if (cookie.getName().equals("ck_masterpasswd_save")) {
          ck_masterpasswd_save = cookie.getValue(); // Y, N
        }
      }
    }

    // <input type='text' class="form-control" name='id' id='id'
    // th:value='${ck_id }' required="required"
    // style='width: 30%;' placeholder="아이디" autofocus="autofocus">
    model.addAttribute("ck_masterid", ck_masterid);

    // <input type='checkbox' name='id_save' value='Y'
    // th:checked="${ck_id_save == 'Y'}"> 저장
    model.addAttribute("ck_masterid_save", ck_masterid_save);

    model.addAttribute("ck_masterpasswd", ck_masterpasswd);
    model.addAttribute("ck_masterpasswd_save", ck_masterpasswd_save);

    model.addAttribute("ck_masterid_save", "Y");
    model.addAttribute("ck_masterpasswd_save", "Y");

    return "master/login_cookie"; // templates/member/login_cookie.html
  }

  /**
   * Cookie 기반 로그인 처리
   * 
   * @param session
   * @param request
   * @param response
   * @param model
   * @param id          아이디
   * @param passwd      패스워드
   * @param id_save     아이디 저장 여부
   * @param passwd_save 패스워드 저장 여부
   * @return
   */
  @PostMapping(value = "/login")
  public String login_proc(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
      String masterid, String masterpasswd, @RequestParam(value = "masterid_save", defaultValue = "") String masterid_save,
      @RequestParam(value = "masterpasswd_save", defaultValue = "") String masterpasswd_save) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("masterid", masterid);
    map.put("masterpasswd", masterpasswd);

    int cnt = this.masterProc.login(map);
    System.out.println("->login_proc cnt:" + cnt);

    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      // id를 이용하여 회원정보를 조회
      MasterVO masterVO = this.masterProc.readById(masterid);
      session.setAttribute("masterno", masterVO.getMasterno());

//    int memberno=(int)session.getAttribute("memberno"); // 세션(session)에서 가져오기
      session.setAttribute("masterid", masterVO.getMasterid());

//cookie 관련코드-------------------------------------------------

// id 관련 쿠기 저장
// -------------------------------------------------------------------
      if (masterid_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
        Cookie ck_masterid = new Cookie("ck_masterid", masterid);
        ck_masterid.setPath("/"); // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_masterid.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
        response.addCookie(ck_masterid); // id 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_masterid = new Cookie("ck_masterid", "");
        ck_masterid.setPath("/");
        ck_masterid.setMaxAge(0);
        response.addCookie(ck_masterid); // id 저장
      }

      // id를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_masterid_save = new Cookie("ck_masterid_save", masterid_save);
      ck_masterid_save.setPath("/");
      ck_masterid_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_masterid_save);
// -------------------------------------------------------------------

// -------------------------------------------------------------------
// Password 관련 쿠기 저장
// -------------------------------------------------------------------
      if (masterpasswd_save.equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_masterpasswd = new Cookie("ck_masterpasswd", masterpasswd);
        ck_masterpasswd.setPath("/");
        ck_masterpasswd.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_masterpasswd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_masterpasswd = new Cookie("ck_masterpasswd", "");
        ck_masterpasswd.setPath("/");
        ck_masterpasswd.setMaxAge(0);
        response.addCookie(ck_masterpasswd);
      }
      // passwd를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_masterpasswd_save = new Cookie("ck_masterpasswd_save", masterpasswd_save);
      ck_masterpasswd_save.setPath("/");
      ck_masterpasswd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_masterpasswd_save);
      // -------------------------------------------------------------------
//--------------------------------------------------------------  
      return "redirect:/";
    } else {
      model.addAttribute("code", "login_fail");
      return "master/msg";
    }
  }

//-------------------------------------------
//Cookie 사용 로그인 관련 코드 종료
//-------------------------------------------
  
  /**
   * 패스워드 수정 폼
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value="/passwd_update_form")
  public String passwd_update_form(HttpSession session, Model model) {
    int masterno = (int)session.getAttribute("masterno"); // session에서 가져오기
    
    MasterVO masterVO = this.masterProc.read(masterno);
    
    model.addAttribute("masterVO", masterVO);
    
    return "master/passwd_update_form"; // /member/passwd_update_form.html   
  }
  
  /**
   * 현재 패스워드 확인
   * @param session
   * @param current_passwd
   * @return 1: 일치, 0: 불일치
   */
  @PostMapping(value="/passwd_check")
  @ResponseBody
  public String passwd_check(HttpSession session, @RequestBody String json_src) {
    System.out.println("-> json_src: " + json_src); // json_src: {"current_passwd":"1234"}
    JSONObject src = new JSONObject(json_src); // String -> JSON
    String current_passwd =  (String)src.get("current_passwd"); // 값 가져오기
    // System.out.println("-> current_passwd: " + current_passwd);
    
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      
    }
    
    int masterno = (int)session.getAttribute("masterno"); // session에서 가져오기
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("masterno", masterno);
    // map.put("passwd", new Security().aesEncode(current_passwd));
    map.put("masterpasswd", current_passwd);
    
    int cnt = this.masterProc.passwd_check(map);
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
    System.out.println(json.toString());
    
    return json.toString();   
  }
  
  /**
   * 패스워드 변경
   * http://localhost:9091/member/passwd_update_proc?current_passwd=00000000000&passwd=7777
   * @param session
   * @param model
   * @param current_passwd 현재 패스워드
   * @param passwd 새로운 패스워드
   * @return
   */
  @PostMapping(value="/passwd_update_proc")
  public String passwd_update_proc(HttpSession session, 
                                                    Model model, 
                                                    String current_passwd, 
                                                    String masterpasswd) {
    

      int masterno = (int)session.getAttribute("masterno"); // session에서 가져오기
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("masterno", masterno);

      map.put("masterpasswd",current_passwd); // 현재 패스워드 이름 주의
      

      int cnt = this.masterProc.passwd_check(map); 
      
      if (cnt == 0) { // 현재 패스워드 불일치
        model.addAttribute("code", "passwd_not_equal");
        model.addAttribute("cnt", 0);
        
      } else { // 현재 패스워드 일치
        HashMap<String, Object> map_new_passwd = new HashMap<String, Object>();
        map_new_passwd.put("masterno", masterno);

        map_new_passwd.put("masterpasswd",masterpasswd); // 새로운 패스워드

        
        int passwd_change_cnt = this.masterProc.passwd_update(map_new_passwd);
        
        if (passwd_change_cnt == 1) {
          model.addAttribute("code", "passwd_change_success");
          model.addAttribute("cnt", 1);
        } else {
          model.addAttribute("code", "passwd_change_fail");
          model.addAttribute("cnt", 0);
        }
      }

      return "master/msg";   // /templates/member/msg.html
    }

  }
  
 

  


