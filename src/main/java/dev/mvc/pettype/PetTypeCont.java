package dev.mvc.pettype;

import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.pet.PetProcInter;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/petType")
public class PetTypeCont {

    @Autowired
    @Qualifier("dev.mvc.pettype.PetTypeProc")
    private PetTypeProcInter petTypeProc;

    @Autowired
    @Qualifier("dev.mvc.matecommunity.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @Autowired
    @Qualifier("dev.mvc.pet.PetProc")
    private PetProcInter petProc;

    @GetMapping("/list")
    public String list(Model model, HttpSession session){
//        if(session.getAttribute("masterid") == null) {
            ArrayList<PetTypeVO> list = this.petTypeProc.list();
            model.addAttribute("list", list);
//        }else{
//            return "redirect:/master/login";
//        }
        return "petType/list";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);
        int petTypeNo = (int) jsonObject.get("petTypeNo");

        int cnt = this.petTypeProc.delete(petTypeNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        System.out.println(cnt);

        return json.toString();
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);
        int petTypeNo = (int) jsonObject.get("petTypeNo");
        String typeName = String.valueOf(jsonObject.get("typeName"));

        int cnt = this.petTypeProc.update(typeName, petTypeNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        System.out.println(cnt);

        return json.toString();
    }

    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);

        int masterNo = (int) jsonObject.get("masterNo");
        String typeName = String.valueOf(jsonObject.get("typeName"));

        PetTypeVO petTypeVO = new PetTypeVO();
        petTypeVO.setPetType(typeName);
        petTypeVO.setMasterNo(masterNo);

        int cnt = this.petTypeProc.create(petTypeVO);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        System.out.println(cnt);

        return json.toString();
    }
}
