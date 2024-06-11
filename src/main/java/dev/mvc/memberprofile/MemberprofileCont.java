package dev.mvc.memberprofile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.MemberTool;
import dev.mvc.tool.Upload;

@Controller
@RequestMapping(value = "/memberprofile")
public class MemberprofileCont {
  @Autowired
  @Qualifier("dev.mvc.memberprofile.MemberprofileProc")
  private MemberprofileProcInter memberprofileProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @PostMapping(value = "/create")
  public String create(HttpServletRequest request, Model model, int memberno, MemberprofileVO memberprofileVO) {
    String upDir = Memberprofile.getUploadDir();

    List<MultipartFile> fnamesMF = memberprofileVO.getFnamesMF();

    if (fnamesMF != null && !fnamesMF.isEmpty()) {
      for (MultipartFile mf : fnamesMF) {
        if (mf.getSize() > 0) {
          try {
            String originalFilename = mf.getOriginalFilename();
            String uniqueFilename = Upload.saveFileSpring(mf, upDir);

            String thumbnail = null;
            if (Tool.isImage(originalFilename)) {
              thumbnail = Tool.preview(upDir, uniqueFilename, 200, 150);
            }

            MemberprofileVO newProfile = new MemberprofileVO();
            newProfile.setMemberno(memberno);
            newProfile.setFile1(originalFilename);
            newProfile.setFile1saved(uniqueFilename);
            newProfile.setThumbfile(thumbnail);
            newProfile.setFilesize(mf.getSize());

            memberprofileProc.create_file(memberno); // 데이터베이스에 프로필 정보 저장
          } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 파일 삭제
            new File(upDir + File.separator + mf.getOriginalFilename()).delete();
          }
        }
      }
    }

    model.addAttribute("memberno", memberno);

    return "/memberprofile/update_file";
  }

  @GetMapping(value = "/update_file")
  public String update_file(Model model, int memberno) {
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    ArrayList<MemberprofileVO> list = this.memberprofileProc.read_file(memberno);
        
    MemberprofileVO memberprofileVO = list.get(0);
    model.addAttribute("memberprofileVO", memberprofileVO);
    
    System.out.println(list);
    
    return "/memberprofile/update_file";
  }

  @PostMapping(value = "/update_file")
  public String update_file(HttpServletRequest request, RedirectAttributes ra, @RequestParam("memberno") int memberno,
      @RequestParam("multiFile") List<MultipartFile> multiFileList, MemberprofileVO memberprofileVO) {
    String upDir = Memberprofile.getUploadDir();

    System.out.println("upDir : " + upDir);

    int origin = this.memberprofileProc.read_file(memberno).get(0).getMprofileno();

    
    if (multiFileList != null && !multiFileList.isEmpty() && !multiFileList.get(0).getOriginalFilename().isEmpty()) {
      this.memberprofileProc.delete_others(memberno, origin);

      for (int i = 0; i < multiFileList.size(); i++) {
        try {
          String originalFilename = multiFileList.get(i).getOriginalFilename();
          String uniqueFilename = Upload.saveFileSpring(multiFileList.get(i), upDir);

          if (i < this.memberprofileProc.read_file(memberno).size()) {
            memberprofileVO = this.memberprofileProc.read_file(memberno).get(i);
          } else {
            memberprofileVO = new MemberprofileVO();
          }

          String thumbnail = null;
          if (Tool.isImage(originalFilename)) {
            thumbnail = Tool.preview(upDir, uniqueFilename, 200, 150);
          }

          System.out.println("File1 : " + originalFilename);

          memberprofileVO.setMemberno(memberno);
          memberprofileVO.setFile1(originalFilename);
          memberprofileVO.setFile1saved(uniqueFilename);
          memberprofileVO.setThumbfile(thumbnail);
          memberprofileVO.setFilesize(multiFileList.get(i).getSize());

          

          if (i == 0) {
            memberprofileProc.update_file(memberprofileVO); // 데이터베이스에 프로필 정보 저장
            System.out.println("memberprofileVO : " + memberprofileVO);
          } else {
            System.out.println("memberprofileVO : " + memberprofileVO);
            memberprofileProc.create_other_file(memberprofileVO);
          }

        } catch (Exception e) { // IOException으로 처리
          e.printStackTrace();
          // 에러 발생 시 파일 삭제
          new File(upDir + File.separator + multiFileList.get(i).getOriginalFilename()).delete();
        }

      }
    }
    if (multiFileList == null || multiFileList.isEmpty() || multiFileList.get(0).getOriginalFilename().isEmpty()) {
      memberprofileProc.update_file(memberprofileVO);
    }

    ra.addAttribute("memberno", memberno);
    

    return "redirect:/member/read";
  }

}
