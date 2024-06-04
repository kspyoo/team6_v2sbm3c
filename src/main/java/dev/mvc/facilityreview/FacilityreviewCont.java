package dev.mvc.facilityreview;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.culturefacility.CulturefacilityProc;
import dev.mvc.culturefacility.CulturefacilityVO;
import dev.mvc.member.MemberProc;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class FacilityreviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.facilityreview.FacilityreviewProc") // 이름지정
  private FacilityreviewProcInter facilityreviewProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // 이름지정
  private MemberProc memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.culturefacility.CulturefacilityProc") // 이름지정
  private CulturefacilityProc culturefacilityProc;
  
  public FacilityreviewCont(){
    System.out.println("-> FacilityreviewCont .");
    
    
 
  }
  
  
  /**
   * 댓글 생성
   * 
   * @param session
   * @param facilityreviewVO
   * @return
   */
  @PostMapping(value = "/facilityreview/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody FacilityreviewVO facilityreviewVO) {
    System.out.println("-> 수신 데이터:" + facilityreviewVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상
    facilityreviewVO.setMemberno(memberno);

    int cnt = this.facilityreviewProc.create(facilityreviewVO);

    JSONObject json = new JSONObject();
//    json.put("res", "등록 테스트");
    json.put("res", cnt);

    return json.toString();
  }

  /**
   * 최신 댓글 500건 출력
   * 
   * @param culturefno
   * @return
   */
  @GetMapping(value = "/facilityreview/list_by_culturefno_join")
  @ResponseBody
  public String list_by_culturefno_join(int culturefno) {
    List<FacilityreviewMemberVO> list = facilityreviewProc.list_by_culturefno_join_500(culturefno);

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    System.out.println("-> obj.toString(): " + obj.toString());

    return obj.toString();
  }

  /**
   * 조회
   * 
   * @param rno
   * @return
   */
  @GetMapping(value = "/facilityreview/read", produces = "application/json")
  @ResponseBody
  public String read(int rno) {
    FacilityreviewVO facilityreviewVO = this.facilityreviewProc.read(rno);

    JSONObject row = new JSONObject();

    row.put("rno", facilityreviewVO.getRno());
    row.put("culturefno", facilityreviewVO.getCulturefno());
    row.put("memberno", facilityreviewVO.getMemberno());
    row.put("reviewcomment", facilityreviewVO.getReviewcomment());
    // row.put("reviewgrade", facilityreviewVO .getReviewgrade());
    row.put("rdate", facilityreviewVO.getRdate());

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    // System.out.println("-> obj.toString(): " + obj.toString());
    return obj.toString();
  }

  /**
   * 수정처리
   * 
   * @param session
   * @param facilityreviewVO
   * @return
   */
  @PostMapping(value = "/facilityreview/update")
  @ResponseBody
  public String update(HttpSession session, @RequestBody FacilityreviewVO facilityreviewVO) {
    System.out.println("-> 수정할 수신 데이터:" + facilityreviewVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == facilityreviewVO.getMemberno()) { // 회원 자신이 쓴글만 수정 가능
      cnt = this.facilityreviewProc.update(facilityreviewVO);
    }

    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }

  /**
   * 삭제처리
   * 
   * @param session
   * @param facilityreviewVO
   * @return
   */
  @PostMapping(value = "/facilityreview/delete")
  @ResponseBody
  public String delete(HttpSession session, @RequestBody FacilityreviewVO facilityreviewVO) {
    System.out.println("-> 삭제할 수신 데이터:" + facilityreviewVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == facilityreviewVO.getMemberno()) { // 회원 자신이 쓴글만 수정 가능
      cnt = this.facilityreviewProc.delete(facilityreviewVO.getRno());
    }

    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }
  
  

  
}
  

