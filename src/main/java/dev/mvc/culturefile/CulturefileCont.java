package dev.mvc.culturefile;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dev.mvc.culturefacility.CulturefacilityProc;
import dev.mvc.culturefacility.CulturefacilityVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CulturefileCont {
  @Autowired
  @Qualifier("dev.mvc.culturefile.CulturefileProc")
  private CulturefileProcInter culturefileProc;

  @Autowired
  @Qualifier("dev.mvc.culturefacility.CulturefacilityProc")
  private CulturefacilityProc culturefacilityProc;

  public CulturefileCont() {
    System.out.println("--> CulturefileCont created.");
  }

  @GetMapping(value = "/culturefile/update_file")
  public String create(@RequestParam("culturefno") int culturefno, Model model, CulturefileVO culturefileVO) {
    CulturefacilityVO culturefacilityVO = this.culturefacilityProc.read(culturefno);
    ArrayList<CulturefileVO> list = this.culturefileProc.read(culturefno);

    System.out.println(list);

    if (list.isEmpty()) {
      this.culturefileProc.create(culturefileVO);
      list = this.culturefileProc.read(culturefno);
    }

    model.addAttribute("list", list);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    model.addAttribute("culturefileVO", list.get(0));
    model.addAttribute("culturefno", culturefno);

    return "/culturefile/update_file";
  }

  @PostMapping(value = "/culturefile/update_file")
  public String create_proc(HttpServletRequest request, @RequestParam("files") MultipartFile[] files, RedirectAttributes ra, int culturefno) {
    String upDir = Culturefile.getUploadDir();
    int upload_count = 0;

    for (MultipartFile mf : files) {
      if (!mf.isEmpty()) {
        String file1 = mf.getOriginalFilename();
        String file1saved = "";
        String thumbfile = "";
        long size1 = mf.getSize();

        if (Tool.checkUploadFile(file1)) {
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) {
            thumbfile = Tool.preview(upDir, file1saved, 200, 150);
          }

          CulturefileVO culturefileVO = new CulturefileVO();
          culturefileVO.setFile1(file1);
          culturefileVO.setFile1saved(file1saved);
          culturefileVO.setThumbfile(thumbfile);
          culturefileVO.setSize1(size1);
          culturefileVO.setCulturefno(culturefno);

          this.culturefileProc.create(culturefileVO);
          upload_count++;
        } else {
          ra.addFlashAttribute("code", "check_upload_file_fail");
          ra.addFlashAttribute("cnt", 0);
          ra.addFlashAttribute("url", "/culture/msg");
          return "redirect:/culturefile/msg";
        }
      }
    }

    ra.addAttribute("culturefno", culturefno);
    ra.addAttribute("upload_count", upload_count);
    ra.addAttribute("url", "msg");
    ra.addFlashAttribute("code", "create_success");
    ra.addFlashAttribute("cnt", upload_count);

    return "redirect:/culturefile/msg";
  }

  @PostMapping(value = "/culturefile/delete_file")
  public String delete_file(@RequestParam("fano") int fano, RedirectAttributes ra, CulturefileVO culturefileVO) {
    culturefileVO = this.culturefileProc.readByFano(fano);
    int culturefno = culturefileVO.getCulturefno();

    this.culturefileProc.delete(fano);

    ra.addAttribute("culturefno", culturefno);
    return "redirect:/culturefile/update_file";
  }

  @GetMapping(value = "/culturefile/msg")
  public ModelAndView msg(@RequestParam("url") String url) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/culturefile/" + url);
    return mav;
  }
}
