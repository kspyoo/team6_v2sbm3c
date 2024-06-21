package dev.mvc.pet;

import dev.mvc.petprofile.PetProfileProcInter;
import dev.mvc.pettype.PetTypeProcInter;
import dev.mvc.pettype.PetTypeVO;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/pet")
public class PetCont {

    @Autowired
    @Qualifier("dev.mvc.pet.PetProc")
    private PetProcInter petProc;

    @Autowired
    @Qualifier("dev.mvc.petprofile.PetProfileProc")
    private PetProfileProcInter petProfileProc;

    @Autowired
    @Qualifier("dev.mvc.pettype.PetTypeProc")
    private PetTypeProcInter PetTypeProc;

    @GetMapping("/create")
    public String createForm(HttpSession session, Model model){
        if (session.getAttribute("memberno") != null) {
            ArrayList<PetTypeVO> petTypeList = this.PetTypeProc.list();
            model.addAttribute("petTypeList", petTypeList);

            return "pet/create";
        }else{
            return "redirect:/member/login";
        }
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

    @GetMapping("/myPetInfo")
    public String myPetInfo(@RequestParam(defaultValue = "0") int petNo, Model model,HttpSession session){
        if (session.getAttribute("memberno") != null) {
            model.addAttribute("petNo", petNo);

            ArrayList<PetTypeVO> petTypeList = this.PetTypeProc.list();
            model.addAttribute("petTypeList", petTypeList);

            return "pet/myPetInfo";
        }else{
            return "redirect:/member/login";
        }
    }

    @GetMapping("/read")
    public String read(@RequestParam(defaultValue = "0") int petNo, Model model){
        model.addAttribute("petNo", petNo);

        ArrayList<PetTypeVO> petTypeList = this.PetTypeProc.list();
        model.addAttribute("petTypeList", petTypeList);

        return "pet/read";
    }

    @GetMapping("/petInfo")
    @ResponseBody
    public PetJoinVO readOne(@RequestParam int petNo){
        System.out.println(petNo);
        PetJoinVO petVO = this.petProc.petInfo(petNo);
        System.out.println(petVO.getPetNo());
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
        String petName = String.valueOf(jsonObject.get("petName"));
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

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") int memberNo, HttpSession session, Model model){
        ArrayList<PetJoinVO> petVOList = new ArrayList<PetJoinVO>();
        int cnt = 0;

        ArrayList<PetTypeVO> petTypeList = this.PetTypeProc.list();
        model.addAttribute("petTypeList", petTypeList);

        if (String.valueOf(memberNo).equals("0")){ // 보고싶은 회원번호가 없는경우
            if (session.getAttribute("memberno") != null){ // 본인의 리스트를 볼 경우
                int myMemberNo = (int) session.getAttribute("memberno");
                petVOList = this.petProc.petInfoList(myMemberNo);

                cnt = this.petProc.petInfoCnt(myMemberNo);

                model.addAttribute("memberNo", myMemberNo);
                model.addAttribute("cnt", cnt);
                model.addAttribute("petVOList", petVOList);

                return "pet/list";
            }else{ // 받아온 회원번호도 없고 로그인도 안된경우
                return "redirect:/member/login";
            }
        }else{
            petVOList = this.petProc.petInfoList(memberNo);
            cnt = this.petProc.petInfoCnt(memberNo);

            model.addAttribute("memberNo", memberNo);
            model.addAttribute("cnt", cnt);
            model.addAttribute("petVOList", petVOList);
            return "pet/list";
        }
    }
}
