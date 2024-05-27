package dev.mvc.member;

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
import dev.mvc.memberprofile.Memberprofile;
import dev.mvc.memberprofile.MemberprofileProcInter;
import dev.mvc.memberprofile.MemberprofileVO;
import dev.mvc.team6_v2sbm3c.MsgCont;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
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
  @Qualifier("dev.mvc.memberprofile.MemberprofileProc")
  private MemberprofileProcInter memberprofileProc;
  
  @Autowired
  @Qualifier("dev.mvc.login.LoginProc")
  private LoginProcInter loginProc;
  
  @Autowired
  Security security;

  public MemberCont() {
  }

  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String id) {
    int cnt = this.memberProc.checkID(id);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    return obj.toString();
  }

  /**
   * 회원 가입 폼
   * 
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value = "/create")
  public String create_form(Model model, MemberVO memberVO, MemberprofileVO memberprofileVO) {
    return "member/create";
  }

  @PostMapping(value = "/create")
  public String create_proc(Model model, MemberVO memberVO, MemberprofileVO memberprofileVO) {
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());

    if (checkID_cnt == 0) {
      memberVO.setStatus("1");
      int cnt = this.memberProc.create(memberVO);
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("name", memberVO.getName());
        model.addAttribute("id", memberVO.getId());
        memberVO = this.memberProc.readById(memberVO.getId());
        memberprofileProc.create_file(memberVO.getMemberno());
        
      } else {
        model.addAttribute("code", "create_fail");
      }
      model.addAttribute("cnt", cnt);
    } else { // id 중복
      model.addAttribute("code", "create_fail");
      model.addAttribute("cnt", 0);
    }
    return "member/msg";
  }

  /**
   * 로그아웃
   * 
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value = "/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate(); // 모든 세션 변수 삭제
    return "redirect:/";
  }

  /**
   * 로그인
   * 
   * @param model
   * @param request 회원번호
   * @return 회원 정보
   */
  @GetMapping(value = "/login")
  public String login_form(Model model, HttpServletRequest request) {
    // Cookie
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) { // 쿠키가 있을때
      for (int i = 0; i < cookies.length; i++) {
        cookie = cookies[i];

        if (cookie.getName().equals("ck_id")) {
          ck_id = cookie.getValue();
        } else if (cookie.getName().equals("ck_id_save")) {
          ck_id_save = cookie.getValue();
        } else if (cookie.getName().equals("ck_passwd")) {
          ck_passwd = cookie.getValue();
        } else if (cookie.getName().equals("ck_passwd_save")) {
          ck_passwd_save = cookie.getValue();
        }
      }
    } // Cookie

    model.addAttribute("ck_id", ck_id);
    model.addAttribute("ck_id_save", ck_id_save);
    model.addAttribute("ck_passwd", ck_passwd);
    model.addAttribute("ck_passwd_save", ck_passwd_save);

    return "member/login_cookie";
  }

  /**
   * 로그인 처리
   * 
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
  @PostMapping(value = "/login")
  public String login_proc(HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Model model,
      String id,
      String passwd,
      @RequestParam(value = "id_save", defaultValue = "") String id_save,
      @RequestParam(value = "passwd_save", defaultValue = "") String passwd_save,
      MemberprofileVO memberprofileVO) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));

    int cnt = this.memberProc.login(map);
    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      MemberVO memberVO = this.memberProc.readById(id);
    
      memberprofileVO =  this.memberprofileProc.read_file(memberVO.getMemberno());
      
      session.setAttribute("memberno", memberVO.getMemberno());
      session.setAttribute("id", memberVO.getId());
      session.setAttribute("name", memberVO.getName());
      session.setAttribute("mprofileno",memberprofileVO.getMprofileno());
//      String ip = this.security.aesEncode(request.getRemoteAddr());
      String ip = request.getRemoteAddr();
      
      Date rdate = new Date();
      HashMap<String, Object> login_map = new HashMap<String, Object>();
      login_map.put("id",id);
      login_map.put("ip",ip);
      login_map.put("conndate", rdate);
      login_map.put("memberno",memberVO.getMemberno());
      
      this.loginProc.create_login_record(login_map);
      
      System.out.println(ip);

      if (id_save.equals("Y")) { // id 저장하는 경우
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30일
        response.addCookie(ck_id);
      } else { // id 저장안하는 경우
        Cookie ck_id = new Cookie("ck_id", "");
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
      } else { // passwd 저장하지 않을 경우
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }
      // passwd를 저장할지 선택하는 CheckBox 체크 여부
      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_passwd_save);


      return "redirect:/";
    } else {
      model.addAttribute("code", "login_fail");
      System.out.println("로그인 실패");
      return "member/msg";
    }

  }

  /**
   * 수정 처리
   * 
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value = "/update")
  public String update_proc(Model model, MemberVO memberVO) {
    int cnt = this.memberProc.update(memberVO);

    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("name", memberVO.getName());
      model.addAttribute("id", memberVO.getId());
    } else {
      model.addAttribute("code", "update_fail");
    }

    model.addAttribute("cnt", cnt);
    return "member/msg";
  }

  @GetMapping(value = "/read")
  public String read(HttpSession session, Model model, int memberno,MemberprofileVO memberprofileVO,LoginVO loginVO) {
    System.out.println(loginVO);
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    System.out.println(this.memberProc.read(memberno));
    
    // MemberProfileVO를 조회하여 모델에 추가
    memberprofileVO = this.memberprofileProc.read_file(memberno);
    model.addAttribute("memberprofileVO", memberprofileVO);
    
    System.out.println("ddddddddddd" + memberprofileVO);
    
    return "member/read";
  }

  /**
   * 삭제
   * 
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value = "/delete")
  public String delete(Model model, int memberno) {
    MemberVO memberVO = this.memberProc.read(memberno);
//    MemberprofileVO memberprofileVO = this.memberprofileProc.read_file(memberno);
    model.addAttribute("memberVO", memberVO);
//    model.addAttribute("memberprofileVO",memberprofileVO);
    

    return "member/delete";
  }

  /**
   * 회원 Delete process
   * 
   * @param model
   * @param memberno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value = "/delete")
  public String delete_process(HttpSession session, Model model, Integer memberno) {
    int cnt = this.memberProc.delete(memberno);
    
    if (cnt == 1) {
      model.addAttribute("code", "delete_success");
      session.invalidate();
      return "member/msg";
    } else {
      model.addAttribute("code", "delete_fail");
      return "member/msg";
    }
  }

  /**
   * 아이디 찾기
   * 
   * @param name
   * @param phone
   * @param model
   * @return
   */
  @GetMapping("/findIdView")
  public String findId_form() {
    return "/member/findIdView";
  }

  @PostMapping("/findIdView")
  public String findId_proc(HttpSession session, Model model, String name, String phone) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("name", name);
    map.put("phone", phone);

    int cnt = this.memberProc.findIdCheck(map);

    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      MemberVO memberVO = this.memberProc.findId(name, phone);

      model.addAttribute("memberVO", memberVO);
      
      return "/member/findId";
    } else {
      model.addAttribute("code", "find_fail");
      return "member/msg";
    }
  }

  /**
   * 비밀번호 찾기
   * 
   * @param name
   * @param phone
   * @param model
   * @return
   */
  @GetMapping("/findPasswdView")
  public String findPasswd_form() {
    return "/member/findPasswdView";
  }

  @PostMapping("/findPasswdView")
  public String findPasswd_proc(RedirectAttributes ra, HttpSession session, Model model, String name, String phone, String id) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("name", name);
    map.put("phone", phone);
    map.put("id", id);

    int cnt = this.memberProc.findIdCheck(map);

    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      ra.addAttribute("id", id);
      return "redirect:/member/findPasswd";
    } else {
      model.addAttribute("code", "find_fail");
      return "member/msg";
    }
  }
  
  @GetMapping("/findPasswd")
  public String findPasswd(Model model,String id) {
    model.addAttribute("id", id);
    return "/member/findPasswd";
  }
  
  @PostMapping("/findPasswd")
  public String passwdChangeProc(HttpSession session, Model model, String passwd, String id) {
      MemberVO memberVO = this.memberProc.readById(id);
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("memberno", memberVO.getMemberno());
      map.put("passwd", this.security.aesEncode(passwd));

      int passwd_change_cnt = this.memberProc.passwd_update(map);
      
      if (passwd_change_cnt == 1) {
        model.addAttribute("code", "passwd_change_success");
        model.addAttribute("cnt", 1);
    } else {
        model.addAttribute("code", "passwd_change_fail");
        model.addAttribute("cnt", 0);
    }
      return "member/msg";
  }

  /**
   * 패스워드 수정 폼
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value="/passwd_update_form")
  public String passwd_update_form(HttpSession session, Model model) { 
    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
    
    MemberVO memberVO = this.memberProc.read(memberno);
    
    model.addAttribute("memberVO", memberVO);
    
    return "member/passwd_update_form";
  }

  @PostMapping(value = "/passwd_check")
  @ResponseBody
  public String passwd_check(HttpSession session, @RequestBody String json_src) {
    JSONObject src = new JSONObject(json_src);
    String current_passwd = (String) src.get("current_passwd");

    try {
      Thread.sleep(2000);
    } catch (Exception e) {
    }

    int memberno = (int) session.getAttribute("memberno");
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    map.put("passwd", this.security.aesEncode(current_passwd));

    int cnt = this.memberProc.passwd_check(map);

    JSONObject json = new JSONObject();
    json.put("cnt", cnt);

    return json.toString();
  }

  /**
   * 패스워드 변경
   */
  @PostMapping(value = "/passwd_update_proc")
  public String passwd_update_proc(HttpSession session, Model model, String current_passwd, String passwd) {

    int memberno = (int) session.getAttribute("memberno"); 
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    
    map.put("passwd", this.security.aesEncode(current_passwd)); 
    
    int cnt = this.memberProc.passwd_check(map);

    if (cnt == 0) { // 현재 패스워드 불일치
      model.addAttribute("code", "passwd_not_equal");
      model.addAttribute("cnt", 0);

    } else { // 현재 패스워드 일치
      HashMap<String, Object> map_new_passwd = new HashMap<String, Object>();
      map_new_passwd.put("memberno", memberno);
      map_new_passwd.put("passwd", this.security.aesEncode(passwd)); // 새로운 패스워드
      
      int passwd_change_cnt = this.memberProc.passwd_update(map_new_passwd);

      if (passwd_change_cnt == 1) {
        model.addAttribute("code", "passwd_change_success");
        model.addAttribute("cnt", 1);
      } else {
        model.addAttribute("code", "passwd_change_fail");
        model.addAttribute("cnt", 0);
      }
    }
    return "member/msg"; 
  }
  
//  @GetMapping(value="/update_file")
//  public String update_file(HttpSession session, Model model, int memberno) {
//    MemberVO memberVO = this.memberProc.read(memberno);
//    model.addAttribute("memberno",memberno);
//    
//    
//    
//    return "/member/update_file";
//  }
//  
//  @PostMapping(value="/update_file")
//  public String update_file(HttpSession session, Model model, RedirectAttributes ra,
//                                  MemberVO memberVO,MemberprofileVO memberprofileVO) {
//    String file1 = "";
//    String file1saved = "";
//    String thumbfile = "";
//    
//    String upDir = Memberprofile.getUploadDir();
//    MultipartFile mf = memberprofileVO.getFile1MF();
//    
//    file1 = mf.getOriginalFilename();
//    
//    long filesize = mf.getSize();
//    if (filesize > 0) {
//      if (Tool.checkUploadFile(file1) == true) {
//        file1saved = Upload.saveFileSpring(mf, upDir);
//        
//        if(Tool.isImage(file1saved)) {
//          thumbfile = Tool.preview(upDir, file1saved, 200, 150);
//        }
//        
//        memberprofileVO.setFile1(file1);
//        memberprofileVO.setFile1saved(file1saved);
//        memberprofileVO.setThumbfile(thumbfile);
//        memberprofileVO.setFilesize(filesize);
//      } else {
//        ra.addFlashAttribute("code","check_upload_file_fail");
//        ra.addFlashAttribute("cnt",0); // 업로드 실패
//        ra.addFlashAttribute("url","/memberprofile/msg");
//        return "redirect:/member/msg";
//      }
//    } else { // 글만 등록
//      
//    }
//    return "redirect:/member/msg";
//  }
  
  @GetMapping(value="/list")
  public String list(HttpSession session, Model model) {
      ArrayList<MemberVO> list = this.memberProc.list();
      
      for (MemberVO memberVO : list) {
        int memberno = memberVO.getMemberno();
        MemberprofileVO memberprofileVO = this.memberprofileProc.read_file(memberno);
        memberVO.setMprofileno(memberprofileVO.getMprofileno());
        int index = list.indexOf(memberVO);
        list.set(index, memberVO);
      }
      
      model.addAttribute("list", list);
      
      return "member/list"; 

  }
  
}
