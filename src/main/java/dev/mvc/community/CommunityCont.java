package dev.mvc.community;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.ArrayList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dev.mvc.catecommunity.CateCommunityProcInter;
import dev.mvc.catecommunity.CateCommunityVO;
import dev.mvc.communityattachment.Communityattachment;
import dev.mvc.communityattachment.CommunityattachmentProcInter;
import dev.mvc.communityattachment.CommunityattachmentVO;
import dev.mvc.communitylike.CommunityLikeProcInter;
import dev.mvc.communitylike.CommunityLikeVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/community")
@Controller
public class CommunityCont {
  @Autowired
  @Qualifier("dev.mvc.community.CommunityProc")
  private CommunityProcInter comunityProc;

  @Autowired
  @Qualifier("dev.mvc.communityattachment.CommunityattachmentProc")
  private CommunityattachmentProcInter communityattachmentProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProcInter;

  @Autowired
  @Qualifier("dev.mvc.catecommunity.CateCommunityProc")
  private CateCommunityProcInter catecomunityProc;

  @Autowired
  @Qualifier("dev.mvc.communitylike.CommunityLikeProc")
  private CommunityLikeProcInter communityLikeProc;
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  public CommunityCont() {
    System.out.println("-> communitycont created");
  }

  @GetMapping(value = "/create") // http://localhost:1521/community/create
  public String create(CommunityVO communityVO, Model model, HttpSession session) {
    
    
    if (session.getAttribute("memberno") != null) {
      model.addAttribute("memberno", session.getAttribute("memberno"));
      
      
      
      model.addAttribute("communityVO", communityVO);
      
      return "/community/create";
    } else {
      return "redirect:/member/login";
    }

  }

  @PostMapping(value = "/create")
  public String create_process(HttpSession session, Model model, @Valid CommunityVO communityVO,
      BindingResult bindingResult, @RequestParam(name = "file") MultipartFile file) {
    if (bindingResult.hasErrors()) {

      return "/community/create";
    }

    int memberno = (int) session.getAttribute("memberno");
    communityVO.setMemberno(memberno);
    int cnt = this.comunityProc.create(communityVO);
    if (session.getAttribute("memberno") != null) {
      
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("title", communityVO.getTitle());

      } else {
        model.addAttribute("code", "create_fail");
      }

      if (file.getSize() > 0) {
        CommunityattachmentVO communityattachmentVO = new CommunityattachmentVO();
        String upDir = Communityattachment.getUploadDir();
        String filesaved = Upload.saveFileSpring(file, upDir);
        String thumb = Tool.preview(upDir, filesaved, 200, 150);
        long filesize = file.getSize();
        communityattachmentVO.setThumbfile(thumb);
        communityattachmentVO.setFilename(file.getOriginalFilename());
        communityattachmentVO.setFilesize(filesize);
        cnt = this.communityattachmentProc.create(communityattachmentVO);
        model.addAttribute("communityattachmentVO", communityattachmentVO);
        
      }

      
      model.addAttribute("cnt", cnt);

      return "/community/msg"; // /templates/cate/msg.html
    } else {
      return "redirect:/member/login";
    }
  }

  @GetMapping("/list_all")
  public String list_all(Model model, @RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = "") String word){

    
    ArrayList<attachmentVO> list = this.comunityProc.list();
    model.addAttribute("list", list);
    

    word = Tool.checkNull(word).trim();
    // System.out.println("--> word: " + word);
    HashMap<String, Object> map = new HashMap<String, Object>();

    map.put("word", word);
    map.put("now_page", now_page);

    // 페이징 목록
    ArrayList<attachmentVO> list2= this.comunityProc.list_search_paging(word, now_page,
        this.record_per_page);
    model.addAttribute("list2", list2);
    model.addAttribute("word", word);

    // 페이징 버튼 목록
    int search_count = this.comunityProc.list_search_count(word);

    String paging = this.comunityProc.pagingBox(now_page, word, "/community/list_all", search_count,
        this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    model.addAttribute("word", word);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "community/list_all";

  }

  @GetMapping("/read")
  public String read(Model model, @RequestParam(name = "communityno") int communityno, HttpSession session,@RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = "") String word, CommunityLikeVO communityLikeVO ) {
    if (session.getAttribute("memberno") != null) {
      model.addAttribute("memberno", session.getAttribute("memberno"));
      attachmentVO attachmentVO = this.comunityProc.read(communityno);
      model.addAttribute("attachmentVO", attachmentVO);

      HashMap<String, Object>map = new HashMap<>();
      map.put("communityno", communityno);
      map.put("memberno", session.getAttribute("memberno"));
      int rcnt_count = this.communityLikeProc.rcnt_count(map);
      model.addAttribute("rcnt_count",rcnt_count);
      System.out.println(rcnt_count);
      
      
     int likecheck = communityLikeVO.getLikecheck();
     model.addAttribute("likecheck",likecheck);
     
     int cnt = this.comunityProc.vcnt(communityno);
      model.addAttribute("cnt",cnt);

      
      ArrayList<attachmentVO> list= this.comunityProc.list_search_paging(word, now_page,
          this.record_per_page);
      model.addAttribute("list", list);
      model.addAttribute("word", word);

      // 페이징 버튼 목록
      int search_count = this.comunityProc.list_search_count(word);

      String paging = this.comunityProc.pagingBox(now_page, word, "/community/list_search", search_count,
          this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);

      model.addAttribute("word", word);
      model.addAttribute("search_count",search_count);
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "community/read";
    } else {
      return "redirect:/member/login";
    }

  }

  @GetMapping("/update/{communityno}")
  public String update(Model model, @PathVariable("communityno") int communityno, HttpSession session,@RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = "") String word) {

    if (session.getAttribute("memberno") != null) {
      model.addAttribute("memberno", session.getAttribute("memberno"));
      attachmentVO attachmentVO = this.comunityProc.read(communityno);
      model.addAttribute("attachmentVO", attachmentVO);

      ArrayList<attachmentVO> list= this.comunityProc.list_search_paging(word, now_page,
          this.record_per_page);
      model.addAttribute("list", list);
      model.addAttribute("word", word);

      // 페이징 버튼 목록
      int search_count = this.comunityProc.list_search_count(word);

      String paging = this.comunityProc.pagingBox(now_page, word, "/community/list_search", search_count,
          this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);

      model.addAttribute("word", word);
      model.addAttribute("search_count",search_count);
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      return "community/update";
    } else {
      return "redirect:/member/login";
    }
  }

  @PostMapping("/update/{communityno}")
  public String update_process(Model model, @Valid CommunityVO communityVO,
      @PathVariable("communityno") int communityno, @RequestParam(name = "file", required = false) MultipartFile file,
      HttpSession session) {
    
      model.addAttribute("memberno", session.getAttribute("memberno"));
      if (session.getAttribute("memberno") != null) {
      int cnt = this.comunityProc.update(communityVO);

      if (cnt == 1) {
        model.addAttribute("code", "update_success");
        model.addAttribute("title", communityVO.getTitle());

      } else {
        model.addAttribute("code", "update_fail");
      }
      model.addAttribute("cnt", cnt);
      if (file.getSize() > 0) {
        CommunityattachmentVO communityattachmentVO = new CommunityattachmentVO();
        String upDir = Communityattachment.getUploadDir();
        String filesaved = Upload.saveFileSpring(file, upDir);
        String thumb = Tool.preview(upDir, filesaved, 200, 150);
        long filesize = file.getSize();
        communityattachmentVO.setThumbfile(thumb);
        communityattachmentVO.setFilename(file.getOriginalFilename());
        communityattachmentVO.setFilesize(filesize);
        communityattachmentVO.setCommunityno(communityno);
        this.communityattachmentProc.create_image(communityattachmentVO);
        model.addAttribute("communityattachmentVO", communityattachmentVO);
      }
      
      
      return "community/msg";
    } else {
      return "redirect:/member/login";
    }
     
  }

  @GetMapping("/delete/{communityno}")
  public String delete(Model model, @PathVariable("communityno") Integer communityno) {
    attachmentVO attachmentVO = this.comunityProc.read(communityno);
    model.addAttribute("attachmentVO", attachmentVO);

    return "community/delete";
  }

  @PostMapping("/delete/{communityno}")
  public String delete_process(Model model, @PathVariable("communityno") Integer communityno,
      @Valid CommunityVO communityVO) {
    attachmentVO attachmentVO = this.comunityProc.read(communityno);
    model.addAttribute("attachmentVO", attachmentVO);

    this.communityattachmentProc.delete(attachmentVO.getCano());

    int cnt = this.comunityProc.delete(communityno);
    model.addAttribute("communityno", communityno);

    if (cnt == 1) {
      model.addAttribute("cdoe", "delete_success");
      model.addAttribute("title", attachmentVO.getTitle());
    } else {
      model.addAttribute("code", "delete_fail");
    }
    model.addAttribute("cnt", cnt);
    return "community/msg";
  }

  @GetMapping("/list_search")
  public String list_search(@RequestParam(name = "now_page", defaultValue = "1") int now_page,
      @RequestParam(name = "word", defaultValue = "") String word, Model model,
      CommunityattachmentVO communityattachmentVO) {

    communityattachmentVO = new CommunityattachmentVO();
    model.addAttribute("communityattachmentVO", communityattachmentVO);
//    ArrayList<attachmentVO> list = this.comunityProc.list();
//    model.addAttribute("list", list);
    
    
    word = Tool.checkNull(word).trim();
    // System.out.println("--> word: " + word);
    HashMap<String, Object> map = new HashMap<String, Object>();
    
    
    map.put("word", word);
    map.put("now_page", now_page);
    
    
    // 페이징 목록
    ArrayList<attachmentVO> list = this.comunityProc.list_search_paging(word, now_page,
        this.record_per_page);
    model.addAttribute("list", list);
    model.addAttribute("word", word);
   
    // 페이징 버튼 목록
    int search_count = this.comunityProc.list_search_count(word);

    String paging = this.comunityProc.pagingBox(now_page, word, "/community/list_search", search_count,
        this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    model.addAttribute("search_count",search_count);
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "community/list_search"; // /cate/list_search.html

  }
  
  
}