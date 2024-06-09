package dev.mvc.petprofile;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/petProfile")
public class PetProfileCont {

    @Autowired
    @Qualifier("dev.mvc.petprofile.PetProfileProc")
    private PetProfileProcInter petProfileProc;

    @GetMapping("/create")
    public String createForm(HttpSession session, int petNo, Model model){
        if (session.getAttribute("memberno") != null){
            model.addAttribute("petNo", petNo);
            return "petProfile/create";
        }else{
            return "redirect:/member/login";
        }
    }
}
