package dev.mvc.notice;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.memberprofile.MemberprofileVO;
import dev.mvc.noticeimg.NoticeimgProcInter;
import dev.mvc.noticeimg.NoticeimgVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/notice")
@Controller
public class NoticeCont {
  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc")
  private NoticeProcInter noticeProc;

  @Autowired
  @Qualifier("dev.mvc.noticeimg.NoticeimgProc")
  private NoticeimgProcInter noticeimgProc;

  @GetMapping(value = "/list_all")
  public String list_all(HttpSession session, Model model, @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("now_page", now_page);

    System.out.println("now_page : " + now_page);
    ArrayList<NoticeVO> list_all = this.noticeProc.paging(map);
    model.addAttribute("list_all", list_all);

    model.addAttribute("word", word);

    int search_count = this.noticeProc.list_search_count(map);
    String paging = this.noticeProc.pagingBox(now_page, word, "/notice/list_all", search_count, Notice.RECORD_PER_PAGE,
        Notice.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    model.addAttribute("search_count", search_count);

    int no = search_count - ((now_page - 1) * Notice.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    return "notice/list_all";
  }

  @GetMapping(value = "/create")
  public String create(HttpSession session, Model model, NoticeVO nocticeVO) {

    if (session.getAttribute("masterno") == null) {
      return "./master/login";
    }
    int masterno = (int) session.getAttribute("masterno");

    model.addAttribute("masterno", masterno);
    return "notice/create";
  }

  @PostMapping(value = "/create")
  public String create(HttpServletRequest request, HttpSession session, NoticeVO noticeVO, RedirectAttributes ra,
      @RequestParam(value = "multiFile") List<MultipartFile> multiFileList, Model model, NoticeimgVO noticeimgVO) {

    int masterno = (int) session.getAttribute("masterno");
    String upDir = Notice.getUploadDir();
    String nfile =Upload.saveFileSpring(multiFileList.get(0), upDir);
    
    noticeVO.setMasterno(masterno);
    noticeVO.setNfile(nfile);
    int cnt = this.noticeProc.create(noticeVO);
    
    ArrayList<NoticeVO> list = this.noticeProc.list_all();
    System.out.println("list : " + list);
    NoticeVO nvo = (NoticeVO) list.get(0);
    
    // 이미지 시작

    

    if (multiFileList != null && !multiFileList.isEmpty()) {
      for (MultipartFile mf : multiFileList) {
        try {
          String originalFilename = mf.getOriginalFilename();
          String uniqueFilename = Upload.saveFileSpring(mf, upDir);

          noticeimgVO = new NoticeimgVO();

          String thumbnail = null;
          if (Tool.isImage(originalFilename)) {
            thumbnail = Tool.preview(upDir, uniqueFilename, 200, 150);
          }
          int noticeno = nvo.getNoticeno();
          noticeimgVO.setNoticeno(noticeno);
          noticeimgVO.setNfile(originalFilename);
          noticeimgVO.setFilesaved(uniqueFilename);
          noticeimgVO.setThumbfile(thumbnail);
          noticeimgVO.setFilesize(mf.getSize());

          this.noticeimgProc.create_file(noticeimgVO);
        } catch (Exception e) {
          e.printStackTrace();
          new File(upDir + File.separator + mf.getOriginalFilename()).delete();
        }
      }
      // 이미지 종료
    }
    
    if (cnt == 1) {
      return "redirect:/notice/list_all";
    } else {
      ra.addAttribute("code", "create_fail");
      ra.addAttribute("cnt", 0);
      ra.addAttribute("url", "/notice_msg");
      return "redirec:/notice/msg";
    }
  }

  @GetMapping(value = "/read")
  public String read(Model model, Integer noticeno) {

    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    ArrayList<NoticeimgVO> list = this.noticeimgProc.read_file(noticeno);
    
    System.out.println("list : " + list.getClass());
    
    model.addAttribute("list", list);
    model.addAttribute("noticeVO", noticeVO);

    return "notice/read";
  }

  @GetMapping(value = "/update")
  public String update(HttpSession session, Model model, int noticeno, RedirectAttributes ra) {
    if (session.getAttribute("masterno") == null) {
      return "./master/login";
    }

    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    model.addAttribute("noticeVO", noticeVO);
    System.out.println("noticeVO : " + noticeVO);

    return "notice/update";
  }

  @PostMapping(value = "update")
  public String update(HttpSession session, Model model, NoticeVO noticeVO, RedirectAttributes ra) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("noticeno", noticeVO.getNoticeno());
    map.put("passwd", noticeVO.getPasswd());

    if (this.noticeProc.password_check(map) == 1) {
      this.noticeProc.update(noticeVO);

      ra.addAttribute("noticeno", noticeVO.getNoticeno());

      return "redirect:/notice/list_all";
    } else {
      ra.addFlashAttribute("code", "passwd_fail");
      ra.addFlashAttribute("cnt", 0);
      ra.addAttribute("url", "/notice/msg");

      return "redirect:/notice/msg";
    }
  }

  @GetMapping(value = "/delete")
  public String delete(HttpSession session, Model model, RedirectAttributes ra, int noticeno) {
    if (session.getAttribute("masterno") == null) {
      return "/master/login";
    }
    model.addAttribute("noticeno", noticeno);

    NoticeVO noticeVO = this.noticeProc.read(noticeno);
    model.addAttribute("noticeVO", noticeVO);

    return "/notice/delete";
  }

  @PostMapping(value = "/delete")
  public String delete(RedirectAttributes ra, int noticeno) {
    this.noticeProc.delete(noticeno);

    return "redirect:/notice/list_all";
  }

  @GetMapping(value = "/list_search_paging_grid")
  public String list_search_paging_grid(HttpSession session, Model model,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<NoticeVO> list_all = this.noticeProc.paging(map);
    model.addAttribute("list_all", list_all);
    model.addAttribute("word", word);

    int search_count = this.noticeProc.list_search_count(map);
    String paging = this.noticeProc.pagingBox(now_page, word, "/notice/list_search_paging_grid", search_count,
        Notice.RECORD_PER_PAGE, Notice.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    model.addAttribute("search_count", search_count);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * Notice.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    // /templates/contents/list_by_cateno_search_paging_grid.html
    return "notice/list_search_paging_grid";
  }

}
