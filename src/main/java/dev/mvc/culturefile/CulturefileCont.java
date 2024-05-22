package dev.mvc.culturefile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value= "/culturefile")
@Controller
public class CulturefileCont {
  
  @Autowired
  @Qualifier("dev.mvc.culturefile.CulturefileProc")
  private CulturefileProcInter culturefileProc;

  
  
  
  public CulturefileCont() {
    System.out.println("-> CulturefileCont created.");
  }

  
  @PostMapping(value = "/create")
  public String create(HttpServletRequest request, HttpSession session,
                        Model model, CulturefileVO culturefileVO,
                        RedirectAttributes ra) {

  
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = ""; // 원본 파일명 image
      String file1saved = ""; // 저장된 파일명, image
      String thumbfile = ""; // preview image

      String upDir = Culturefile.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF'
      // value='' placeholder="파일 선택">
      MultipartFile mf = culturefileVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);

      long size1 = mf.getSize(); // 파일 크기
      if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumbfile = Tool.preview(upDir, file1saved, 200, 150);
          }

          culturefileVO.setFile1(file1); // 순수 원본 파일명
          culturefileVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
          culturefileVO.setThumbfile(thumbfile); // 원본이미지 축소판
          culturefileVO.setSize1(size1); // 파일 크기

        } else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/culturefile/msg"); // msg.html, redirect parameter 적용
          return "redirect:/culturefile/msg"; // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------

      // Call By Reference: 메모리 공유, Hashcode 전달
      int culturefno = (int) session.getAttribute("culturefno"); // memberno FK
      culturefileVO.setCulturefno(culturefno);
      int cnt = this.culturefileProc.create(culturefileVO);

      // ------------------------------------------------------------------------------
      // PK의 return
      // ------------------------------------------------------------------------------
      // System.out.println("--> contentsno: " + contentsVO.getContentsno());
      // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
      // parameter 적용
      // ------------------------------------------------------------------------------

      if (cnt == 1) {
        // type 1, 재업로드 발생
        // return "<h1>파일 업로드 성공</h1>"; // 연속 파일 업로드 발생

        // type 2, 재업로드 발생
        // model.addAttribute("cnt", cnt);
        // model.addAttribute("code", "create_success");
        // return "contents/msg";

        // type 3 권장
        // return "redirect:/contents/list_all"; // /templates/contents/list_all.html

        // System.out.println("-> contentsVO.getCateno(): " + contentsVO.getCateno());
        // ra.addFlashAttribute("cateno", contentsVO.getCateno()); // controller ->
        // controller: X

        // ra.addAttribute("cateno", contentsVO.getCateno()); // controller -> controller: O
        return "redirect:/contents/list_by_cateno";

        // return "redirect:/contents/list_by_cateno?cateno=" + contentsVO.getCateno();
        // // /templates/contents/list_by_cateno.html
      } else {
        ra.addFlashAttribute("code", "create_fail"); // DBMS 등록 실패
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/culturefile/msg"); // msg.html, redirect parameter 적용
        return "redirect:/culturefile/msg"; // Post -> Get - param...
      }
  }
  

  
  
}

