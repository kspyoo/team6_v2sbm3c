package dev.mvc.communityattachment;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import dev.mvc.community.CommunityProcInter;
import dev.mvc.community.CommunityVO;
import dev.mvc.community.attachmentVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/communityattachment")
@Controller   
public class CommunityattachmentCont {
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.community.CommunityProc")
  private CommunityProcInter communityProc;
  
@Autowired
@Qualifier("dev.mvc.communityattachment.CommunityattachmentProc")
private CommunityattachmentProcInter communityattachmentProc;

public CommunityattachmentCont() {
  
}
@GetMapping(value = "/create")
public String create(CommunityattachmentVO communityattachmentVO) {
  
  return "communityattachment/create";
}
@PostMapping(value = "/create")
public String create_process(Model model,CommunityattachmentVO communityattachmentVO, RedirectAttributes ra) {
    String filename ="";
    String thumbfile ="";
    
    String upDir = Communityattachment.getUploadDir();
    System.out.println("-> updir" + upDir);

    
    MultipartFile mf  = communityattachmentVO.getFilenameMF();
    filename = mf.getOriginalFilename();
    System.out.println("->원본 파일명 산출" + filename);
    
    long filesize = mf.getSize();
    if(filesize > 0) {
      if(Tool.checkUploadFile(filename) == true) {
        filename = Upload.saveFileSpring(mf, upDir);
        
        if (Tool.isImage(thumbfile)) {
          filename = Tool.preview(upDir, filename, 200, 150);
        }
        
        communityattachmentVO.setFilename(filename);
        communityattachmentVO.setThumbfile(thumbfile);
        communityattachmentVO.setFilesize(filesize);
      }else {
        ra.addFlashAttribute("code","check_upload_file_fail");
        ra.addFlashAttribute("cnt",0);
        ra.addFlashAttribute("url", "/communityattchment/msg");
        return "redirect:/communityattchment/msg";
      }
    }else {
      System.out.println("->글만 등록");
      }
    
    System.out.println("create 이");
    return "community/create";
    
    }
  @GetMapping(value = "/delete")
  public String delete(Model model,@RequestParam int cano,@RequestParam int communityno) {
    attachmentVO attachmentVO = this.communityProc.read(communityno);
    model.addAttribute("attachmentVO",attachmentVO);
     
    this.communityattachmentProc.delete(cano);
    
    return "redirect:/community/update/"+communityno;
  }
  
  
//  @PostMapping(value = "/delete")
//  public String delete_process(@RequestParam int cano,@RequestParam int communityno) {
//    attachmentVO attachmentVO = this.communityProc.read(communityno);
//    
//    this.communityattachmentProc.delete(cano);
//    
//    return "redirect:/community/update";
//  }
}


