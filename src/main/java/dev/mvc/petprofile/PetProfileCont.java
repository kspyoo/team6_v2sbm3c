package dev.mvc.petprofile;

import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

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

    @PostMapping("/create")
    @ResponseBody
    public String create(HttpSession session, int petNo, MultipartFile petProfile){
        JSONObject json = new JSONObject();
        int cnt = -1;

        if (session.getAttribute("memberno") != null) {
            // ------------------------------------------------------------------------------
            // 파일 전송 코드 시작
            // ------------------------------------------------------------------------------

            String file1 = "";          // 원본 파일명 image
            String file1saved = "";   // 저장된 파일명, image

            String upDir = PetProfile.getUploadDir(); // 파일을 업로드할 폴더 준비
            System.out.println("-> upDir: " + upDir);

            // 전송 파일이 없어도 file1MF 객체가 생성됨.
            file1 = petProfile.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
            System.out.println("-> 원본 파일명 산출 file1: " + file1);

            long size1 = petProfile.getSize();  // 파일 크기
            if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
                if (Tool.isImage(file1) == true) { // 업로드 가능한 파일인지 검사
                    PetProfileVO petProfileVO = new PetProfileVO();

                    // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
                    file1saved = Upload.saveFileSpring(petProfile, upDir);

                    petProfileVO.setOgFileName(file1);   // 순수 원본 파일명
                    petProfileVO.setSvFileName(file1saved); // 저장된 파일명(파일명 중복 처리)
                    petProfileVO.setPetNo(petNo);

                    int result = this.petProfileProc.create(petProfileVO);

                    System.out.println("cnt -> "+result);
                    json.put("cnt", result);
                    return json.toString();
                }
            }
            // ------------------------------------------------------------------------------
            // 파일 전송 코드 종료
            // ------------------------------------------------------------------------------
        }

        json.put("cnt", cnt);
        return json.toString();
    }

    @GetMapping("/list")
    public String profileList(Model model, int petNo){
        ArrayList<PetProfileVO> list = this.petProfileProc.list(petNo);

        model.addAttribute("list", list);
        model.addAttribute("petNo", petNo);

        return "petProfile/list";
    }

    @GetMapping("/update")
    public String profileUpdateList(Model model, int petNo){
        ArrayList<PetProfileVO> list = this.petProfileProc.list(petNo);

        model.addAttribute("list", list);
        model.addAttribute("petNo", petNo);

        return "petProfile/updateList";
    }

    @PostMapping("/delete_one")
    @ResponseBody
    public String delete_one(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);

        int petNo = (int) jsonObject.get("petProfileNo");

        int cnt = this.petProfileProc.delete_all(petNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @PostMapping("/delete_all")
    @ResponseBody
    public String delete_all(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);

        int petNo = (int) jsonObject.get("petNo");

        int cnt = this.petProfileProc.delete_all(petNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @GetMapping("/profile_cnt")
    @ResponseBody
    public String profile_cnt(int petNo){
        int cnt = this.petProfileProc.profile_cnt(petNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }
}
