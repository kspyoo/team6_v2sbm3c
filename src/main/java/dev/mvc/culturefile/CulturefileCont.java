package dev.mvc.culturefile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  /**
   * 등록 폼
   * 
   * @param culturefno 문화 파일 번호
   * @return 뷰 이름과 모델을 담은 ModelAndView 객체
   */
  @GetMapping(value = "/culturefile/update_file")
  public String create(@RequestParam("culturefno") int culturefno, Model model,CulturefileVO culturefileVO) {
      CulturefacilityVO culturefacilityVO = this.culturefacilityProc.read(culturefno);
      ArrayList<CulturefileVO> list = this.culturefileProc.read(culturefno);
      
      
 
      System.out.println(culturefileVO);
      model.addAttribute("culturefacilityVO", culturefacilityVO);
      model.addAttribute("culturefileVO",culturefileVO);
      
      System.out.println(list.get(0));
      model.addAttribute("list", list);
      model.addAttribute("culturefno", culturefno); // 매개변수 전달

      return "/culturefile/update_file";
  }


  /**
   * 등록 처리
   * 
   * @param request       HTTP 요청 객체
   * @param culturefileVO 문화 파일 VO
   * @param ra            리다이렉트 속성
   * @param culturefno    문화 파일 번호
   * @return 리다이렉트 URL
   */
  @PostMapping(value = "/culturefile/update_file")
  public String create_proc(HttpServletRequest request, CulturefileVO culturefileVO, RedirectAttributes ra,
      int culturefno) {

//    // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
//    ArrayList<CulturefileVO> culturefileVO_old = culturefileProc.read(culturefileVO.getFano());
//    // -------------------------------------------------------------------
//    // 파일 삭제 시작
//    // -------------------------------------------------------------------
//    String file1saved = culturefileVO_old.getFile1saved(); // 실제 저장된 파일명
//    String thumbfile = culturefileVO_old.getThumbfile(); // 실제 저장된 preview 이미지 파일명
//    long size1 = 0;
//
//    String upDir = Culturefile.getUploadDir(); // C:/kd/deploy/resort_v4sbm3c/contents/storage/
//
//    Tool.deleteFile(upDir, file1saved); // 실제 저장된 파일삭제
//    Tool.deleteFile(upDir, thumbfile); // preview 이미지 삭제
//    // -------------------------------------------------------------------
//    // 파일 삭제 종료
//    // -------------------------------------------------------------------
//   

    // 파일 전송 코드 시작
    String file1 = ""; // 원본 파일명
    String file1saved = ""; // 저장된 파일명
    String thumbfile = ""; // 미리보기 이미지

    String upDir = Culturefile.getUploadDir(); // 파일 업로드 폴더
    System.out.println("-> upDir: " + upDir);

    MultipartFile mf = culturefileVO.getFile1MF();

    file1 = mf.getOriginalFilename(); // 원본 파일명
    System.out.println("-> 원본 파일명 file1: " + file1);

    long size1 = mf.getSize(); // 파일 크기
    int upload_count = 0; // 업로드 횟수 초기화
    if (size1 > 0) { // 파일 크기 체크
      if (Tool.checkUploadFile(file1)) { // 업로드 가능한 파일인지 검사
        file1saved = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          thumbfile = Tool.preview(upDir, file1saved, 200, 150); // 썸네일 생성
        }

        culturefileVO.setFile1(file1); // 원본 파일명
        culturefileVO.setFile1saved(file1saved); // 저장된 파일명
        culturefileVO.setThumbfile(thumbfile); // 썸네일 파일명
        culturefileVO.setSize1(size1); // 파일 크기
        upload_count = 1; // 업로드 성공

        this.culturefileProc.create(culturefileVO);
      } else { // 업로드 불가능한 파일 형식
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 실패 메시지
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/culture/msg"); // 메시지 URL
        return "redirect:/culturefile/msg"; // 리다이렉트
      }
    } else { // 글만 등록하는 경우
      System.out.println("-> 글만 등록");
    }

    // 파일 전송 코드 종료

    ra.addAttribute("culturefno", culturefno); // 리다이렉트 매개변수
    ra.addAttribute("upload_count", upload_count); // 리다이렉트 매개변수
    ra.addAttribute("url", "msg"); // 메시지 URL
    ra.addFlashAttribute("code", "create_success"); // 업로드 성공 메시지
    ra.addFlashAttribute("cnt", 1); // 업로드 성공

    return "redirect:/culturefile/msg"; // 리다이렉트
  }

//파일 삭제 메소드 추가
  @PostMapping(value = "/culturefile/delete")
  public String delete(@RequestParam("fileNo") int fileNo, RedirectAttributes ra) {
    // 파일 삭제 로직 구현
    int success = culturefileProc.delete(fileNo);

    if (success > 0) { // 성공적으로 삭제된 경우
      ra.addFlashAttribute("message", "파일이 성공적으로 삭제되었습니다.");
    } else if (success == 0) { // 삭제된 것이 없는 경우
      ra.addFlashAttribute("warning", "삭제된 파일이 없습니다.");
    } else { // 오류 발생
      ra.addFlashAttribute("error", "파일 삭제 중 오류가 발생했습니다.");
    }

    return "redirect:/culturefile/update_file"; // 삭제 후 해당 페이지로 리다이렉트
  }

  /**
   * 새로고침 방지를 위한 메시지 출력
   * 
   * @param url 메시지 URL
   * @return 뷰 이름과 모델을 담은 ModelAndView 객체
   */
  @GetMapping(value = "/culturefile/msg")
  public ModelAndView msg(@RequestParam("url") String url) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/culturefile/" + url); // URL을 통해 메시지 페이지 설정
    return mav;
  }
}