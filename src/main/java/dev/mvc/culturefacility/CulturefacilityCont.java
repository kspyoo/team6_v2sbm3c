package dev.mvc.culturefacility;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.culturefacility.CulturefacilityVO;
import dev.mvc.culturefile.CulturefileProcInter;
import dev.mvc.facilityreview.FacilityreviewVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/culturefacility")
@Controller
public class CulturefacilityCont {
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.culturefacility.CulturefacilityProc")
  private CulturefacilityProcInter CulturefacilityProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.culturefile.CulturefileProc")
  private CulturefileProcInter culturefileProc;
  
  


  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  public CulturefacilityCont() {
    System.out.println("-> CulturefacilityCont created.");
  }
  
  
@GetMapping(value="/create") // http://localhost:9091/cate/create
public String create(Model model, CulturefacilityVO culturefacilityVO
    ,@RequestParam(name="word", defaultValue="") String word) {
  
  ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search(word);
  model.addAttribute("list", list);
  
  return "culturefacility/create"; // /templates/cate/create.html
}

  /**
   * create 폼 데이터 처리
   * http://localhost:9091/cate/list_search
   * @param model
   * @param cateVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/cate/create
  public String create(Model model, @Valid CulturefacilityVO culturefacilityVO, BindingResult bindingResult, 
      @RequestParam(name="word", defaultValue="") String word,
      @RequestParam(name="now_page", defaultValue="1") int now_page) {

 
    if (bindingResult.hasErrors()) {
      // 유효성 검증 오류가 발생한 경우, 오류 메시지를 모델에 추가
      model.addAttribute("bindingResult", bindingResult);
      // 페이징 목록
      ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.CulturefacilityProc.list_search_count(word);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      String paging = this.CulturefacilityProc.pagingBox(now_page, 
          word, "culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
   
      return "culturefacility/list_search";  // /templates/cate/list_search.html
    }
    
    int cnt = this.CulturefacilityProc.create(culturefacilityVO);
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) { // 등록 성공
      model.addAttribute("code", "create_success");
//      model.addAttribute("cname", culturefacilityVO.getCname());
//      model.addAttribute("namesub", cateVO.getNamesub());
      
      return "redirect:/culturefacility/list_search"; // /cate/list_search.html
      
    } else { // 실패
      model.addAttribute("code", "create_fail");
      return "culturefacility/msg"; // /templates/cate/msg.html
    }
  }
  

    
  
   
  

  /**
   * 조회 + 목록
   * @param model
   * @param culturefno 조회할 문화시설 번호
   * @return
   */
  @GetMapping(value="/read/{culturefno}")
  public String read(Model model, 
                            @PathVariable("culturefno") Integer culturefno, 
                            @RequestParam(name="word", defaultValue = "") String word,
                            @RequestParam(name="now_page", defaultValue="1") int now_page) {
    
    // word = Tool.checkNull(word);
 
    
    
    CulturefacilityVO culturefacilityVO = this.CulturefacilityProc.read(culturefno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    // 페이징 목록
    ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    
    // 페이징 버튼 목록
    int search_count = this.CulturefacilityProc.list_search_count(word);
    String paging = this.CulturefacilityProc.pagingBox(now_page, 
        word, "/culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "culturefacility/read";  // /templates/cate/myPetInfo.html
//    return "culturefacility/read_cookie_reply"
  }
  
  /**
   * 수정폼
   * @param model
   * @param cateno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update/{culturefno}")
  public String update(Model model, 
                                @PathVariable("culturefno") Integer culturefno, 
                                @RequestParam(name="word", defaultValue = "") String word,
                                @RequestParam(name="now_page", defaultValue = "1") int now_page) { 

    CulturefacilityVO culturefacilityVO = this.CulturefacilityProc.read(culturefno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    
    // 페이징 목록
    ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    
    // 페이징 버튼 목록
    int search_count = this.CulturefacilityProc.list_search_count(word);
    String paging = this.CulturefacilityProc.pagingBox(now_page, 
        word, "/culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "culturefacility/update";  // /templates/cate/update.html
    
  }
  /**
   * 수정 처리
   * @param model
   * @param culturefacilityVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/update") // http://localhost:9091/cate/update
  public String update_process(Model model, 
                                            @Valid CulturefacilityVO culturefacilityVO, BindingResult bindingResult, 
                                            @RequestParam(name="word", defaultValue = "") String word,
                                            @RequestParam(name="now_page", defaultValue="1") int now_page) {

    
    if (bindingResult.hasErrors()) {  
      // 유효성 검증 오류가 발생한 경우, 오류 메시지를 모델에 추가
      model.addAttribute("bindingResult", bindingResult);
      // 페이징 목록
      ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.CulturefacilityProc.list_search_count(word);
      String paging = this.CulturefacilityProc.pagingBox(now_page, 
          word, "/culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      return "culturefacility/update";  // /templates/cate/update.html
    }
    
    int cnt = this.CulturefacilityProc.update(culturefacilityVO);
//    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      return "redirect:/culturefacility/update/" + culturefacilityVO.getCulturefno() + "?word=" + Tool.encode(word) + "&now_page=" + now_page;
      
    } else {
      model.addAttribute("code", "update_fail");
      return "culturefacility/msg"; // /templates/cate/msg.html
    }

  }
  
  /**
   * Delete form
   * http://localhost:9091/cate/delete/1
   * @param model
   * @param cateno Category number to delete.
   * @return
   */
  @GetMapping(value="/delete/{culturefno}")
  public String delete(Model model, 
                               @PathVariable("culturefno") Integer culturefno, 
                               @RequestParam(name="word", defaultValue = "") String word,
                               @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    
    CulturefacilityVO culturefacilityVO = this.CulturefacilityProc.read(culturefno);
    model.addAttribute("culturefacilityVO", culturefacilityVO);
    
    // 페이징 목록
    ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    
    // 페이징 버튼 목록
    int search_count = this.CulturefacilityProc.list_search_count(word);
    String paging = this.CulturefacilityProc.pagingBox(now_page, 
        word, "/culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "culturefacility/delete";  // /templates/cate/delete.html
    
  }
  
  /**
   * Delete process
   * @param model
   * @param cateno 삭제할 레코드 번호
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, Integer culturefno, 
                                           @RequestParam(name="word", defaultValue = "") String word,
                                           @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    int cnt = this.CulturefacilityProc.delete(culturefno); // 삭제
    // System.out.println("-> cnt: " + cnt);
    
    model.addAttribute("cnt", cnt);
    
    // ----------------------------------------------------------------------------------------------------------
    // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
    int search_cnt = this.CulturefacilityProc.list_search_count(word);
    if (search_cnt % this.record_per_page == 0) {
      now_page = now_page - 1;
      if (now_page < 1) {
        now_page = 1; // 최소 시작 페이지
      }
    }
    // ----------------------------------------------------------------------------------------------------------
    
    if (cnt == 1) {
      return "redirect:/culturefacility/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
    } else {
      model.addAttribute("code", "delete_fail");
      return "culturefacility/msg"; // /templates/cate/msg.html
    }
  }
  
  
  /**
   * 등록폼 + 검색 목록
   * http://localhost:9091/cate/list_search?word=개발  ← GET Form
   * http://localhost:9091/cate/list_search/개발  ← @PathVariable
   * @param model
   * @param cateVO
   * @param word 검색어
   * @param now_page 현재 페이지 번호
   * @return
   */
  @GetMapping(value="/list_search")
  public String list_search(Model model, CulturefacilityVO culturefacilityVOVO, String word, 
                                     @RequestParam(name="now_page", defaultValue="1") int now_page) {
    // cateVO.setNamesub("-"); // 폼 초기값 설정
    word = Tool.checkNull(word).trim();
    // System.out.println("--> word: " + word);
    

    
    // 페이징 목록
    ArrayList<CulturefacilityVO> list = this.CulturefacilityProc.list_search_paging(word, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    
    // 페이징 버튼 목록
    int search_count = this.CulturefacilityProc.list_search_count(word);
    String paging = this.CulturefacilityProc.pagingBox(now_page, 
        word, "/culturefacility/list_search", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "culturefacility/list_search"; // /cate/list_search.html
  }
  

  

  
  
  
}
