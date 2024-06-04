package dev.mvc.pet;

import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pet")
public class PetCont {

    @Autowired
    @Qualifier("dev.mvc.pet.PetProc")
    private PetProcInter petProc;

    @GetMapping("/create")
    public String createForm(){
        return "pet/create";
    }

    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody String json_src, HttpSession session){
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        PetVO petVO = new PetVO();

        petVO.setPetName(String.valueOf(jsonObject.get("name")));
        petVO.setPetTypeNo((int) jsonObject.get("petType"));
        petVO.setPetGender(String.valueOf(jsonObject.get("gender")));
        petVO.setPetBirth(String.valueOf(jsonObject.get("birth")));
        petVO.setPetDetail(String.valueOf(jsonObject.get("petInfo")).trim());
        petVO.setMemberNo((int) session.getAttribute("memberno"));

        int cnt = this.petProc.create(petVO);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @GetMapping("/read")
    public String read(){
        return "pet/myPetInfo";
    }

    @GetMapping("/petInfo")
    @ResponseBody
    public PetVO readOne(@RequestParam int petNo){
        PetVO petVO = this.petProc.myPetInfo(petNo);
        return petVO;
    }

    @GetMapping("/petInfoCnt")
    @ResponseBody
    public String infoCnt(HttpSession session){
        JSONObject json = new JSONObject();
        int cnt = 0;

        if (String.valueOf(session.getAttribute("name")).equals("null")){
            cnt = -1;
            json.put("cnt", cnt);
            return json.toString();
        }

        cnt = this.petProc.petInfoCnt((int) session.getAttribute("memberno"));
        json.put("cnt", cnt);
        return json.toString();
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody String json_src, HttpSession session){
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        int petNo = (int) jsonObject.get("petNo");
        String petName = String.valueOf(jsonObject.get("제petName"));
        String petDetail = String.valueOf(jsonObject.get("petDetail"));

        int cnt = this.petProc.update(petNo,petName,petDetail);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody String json_src, HttpSession session){
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        int petNo = (int) jsonObject.get("petNo");

        int cnt = this.petProc.delete(petNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }
}
