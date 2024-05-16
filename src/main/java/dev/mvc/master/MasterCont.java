package dev.mvc.master;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.master.MasterVO;

@RequestMapping("/master")
@Controller
public class MasterCont {
  @Autowired
  @Qualifier("dev.mvc.master.MasterProc")
  private MasterProcInter masterProc;

  public MasterCont() {
    System.out.println("->MasterCont created");
  }

  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String id) {
    int cnt = this.masterProc.checkID(id);

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

}
