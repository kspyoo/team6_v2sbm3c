package dev.mvc.team6_v2sbm3c;

import java.util.ArrayList;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class HomeCont {
 
  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }
  
  @GetMapping(value="/") // http://localhost:9091
  public String home(Model model) { // 파일명 return
    
    
    return "index"; // /templates/index.html  
  }
  
}
