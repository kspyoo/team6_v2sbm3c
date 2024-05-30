package dev.mvc.memberprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@RequestMapping(value="/memberprofile")
@Controller
public class MemberprofileCont {
  @Autowired
  @Qualifier("dev.mvc.memberprofile.MemberprofileProc")
  private MemberprofileProcInter memberprofileProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @GetMapping(value="/update_file")
  public String update_file(Model model,MemberprofileVO memberprofileVO,int memberno) {
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO",memberVO);
    
    memberprofileVO = this.memberprofileProc.read_file(memberno);
    model.addAttribute("memberprofileVO", memberprofileVO);

    return "/memberprofile/update_file";
  }
  
  @PostMapping(value="/update_file")
  public String update_file(RedirectAttributes ra, MemberprofileVO memberprofileVO, MemberVO memberVO, int memberno) {

      MemberprofileVO memberprofileVO_old = memberprofileProc.read_file(memberprofileVO.getMemberno());

      String file1saved = memberprofileVO_old.getFile1saved();
      String thumbfile = memberprofileVO_old.getThumbfile();
      long filesize = 0;

      String upDir = Memberprofile.getUploadDir();

//      Tool.deleteFile(upDir, file1saved);
//      Tool.deleteFile(upDir, thumbfile);

      String file1 = "";
      MultipartFile mf = memberprofileVO.getFile1MF();

      file1 = mf.getOriginalFilename();
      filesize = mf.getSize();

      if (filesize > 0) {
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) {
              thumbfile = Tool.preview(upDir, file1saved, 250, 200);
          }
      } else {
          file1="";
          file1saved="";
          thumbfile="";
          filesize=0;
      }
      memberprofileVO.setFile1(file1);
      memberprofileVO.setFile1saved(file1saved);
      memberprofileVO.setThumbfile(thumbfile);
      memberprofileVO.setFilesize(filesize);

      this.memberprofileProc.update_file(memberprofileVO);
      ra.addAttribute("mprofileno", memberprofileVO.getMprofileno());
      ra.addAttribute("memberno", memberprofileVO.getMemberno());

      return "redirect:/member/read";
  }

}
