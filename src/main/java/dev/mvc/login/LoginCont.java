package dev.mvc.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/login")
@Controller
public class LoginCont {
  
  @Autowired
  @Qualifier("dev.mvc.login.LoginProc")
  private LoginProcInter loginProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @GetMapping(value="/login_list")
  public String list(HttpSession session, Model model, MemberVO memberVO) {
    ArrayList<LoginVO> list = this.loginProc.login_list();
    model.addAttribute("list",list);
    
    return "login/login_list";
  }
  
 
}
