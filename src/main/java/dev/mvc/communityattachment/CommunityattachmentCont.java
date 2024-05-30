package dev.mvc.communityattachment;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import dev.mvc.community.CommunityProcInter;
import dev.mvc.community.CommunityVO;
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
public String create_process(HttpServletRequest request,HttpSession session ,Model model,CommunityattachmentVO communityattachmentVO, RedirectAttributes ra, CommunityVO communityVO) {
  
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 시작
    // ------------------------------------------------------------------------------
    String filename = ""; // 원본 파일명 imag
    String thumbfile = ""; // preview image
    String upDir =Communityattachment.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);

    // 전송 파일이 없어도 file1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF'
    // value='' placeholder="파일 선택">  
    MultipartFile mf = communityattachmentVO.getFilenameMF();

    filename = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
    System.out.println("-> 원본 파일명 산출 filename: " + filename);

    long size1 = mf.getSize(); // 파일 크기
    if (size1 > 0) { // 파일 크기 체크
      if (Tool.checkUploadFile(filename) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        filename = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(filename)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumbfile = Tool.preview(upDir, filename, 200, 150);
        }
        communityattachmentVO.setFilename(filename); // 순수 원본 파일명
        communityattachmentVO.setThumbfile(thumbfile); // 원본이미지 축소판
        communityattachmentVO.setFilesize(1000); // 파일 크기
        
      } else { // 전송 못하는 파일 형식
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("url", "/communityattachment/msg"); // msg.jsp, redirect parameter 적용
        return "redirect:/communityattachment/msg"; // Post -> Get - param...
      }

    } else { // 글만 등록하는 경우
      System.out.println("-> 글만 등록");
    }

    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------

    // Call By Reference: 메모리 공유, Hashcode 전달
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    communityVO.setMemberno(memberno);
    int cnt = this.communityProc.create(communityVO);

    // ------------------------------------------------------------------------------
    // PK의 return
    // ------------------------------------------------------------------------------
    // System.out.println("--> contentsno: " + contentsVO.getContentsno());
    // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
    // parameter 적용
    // ------------------------------------------------------------------------------

    if (cnt == 1) {
      // type1
//      return "<h1> 파일 업로드 성공</h1>";  //연속 파일 업로드 발생

//      // type2
//      model.addAttribute("cnt",cnt);
//      model.addAttribute("code","create_success");
//      return "contents/msg";

      // type3 권장
      // return "redirect:/contents/list_all"; // /templates/contents/list_all.html

      return "redirect:/community/list_all?communityno=" + communityVO.getCommunityno();
    } else {
      ra.addFlashAttribute("code", "create_fail");// DBMS 등록 실패
      ra.addFlashAttribute("cnt", 0); // 업로드 실패
      ra.addFlashAttribute("url", "/community/msg"); // msg.jsp, redirect parameter 적용
      return "redirect:/community/msg"; // Post -> Get - param...
    }



}

}
