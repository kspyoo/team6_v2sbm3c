package dev.mvc.sympathy;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.memberprofile.MemberprofileVO;
import dev.mvc.notice.Notice;
import dev.mvc.notice.NoticeVO;
import dev.mvc.tool.Security;

@Component("dev.mvc.sympathy.SympathyProc")
public class SympathyProc implements SympathyProcInter {

  @Autowired
  private SympathyDAOInter sympathyDAO;

  public SympathyProc() {
  }

  @Override
  public int create(SympathyVO sympathyVO) {
    int cnt = this.sympathyDAO.create(sympathyVO);
    return cnt;
  }

  @Override
  public int read(HashMap<String, Object> map) {
    int cnt = this.sympathyDAO.read(map);
    return cnt;
  }
  
  @Override
  public int read_nt(int noticeno) {
    int cnt = this.sympathyDAO.read_nt(noticeno);
    return cnt;
  }

  @Override
  public int delete(HashMap<String, Object> map) {
    int cnt = this.sympathyDAO.delete(map);
    return cnt;
  }

  @Override
  public ArrayList<SympathynoticeVO> read_all(int memberno) {
    ArrayList<SympathynoticeVO> read_all = this.sympathyDAO.read_all(memberno);
    return read_all;
  }

  @Override
  public ArrayList<SympathynoticeVO> list_search(HashMap<String, Object> map) {
    ArrayList<SympathynoticeVO> list= this.sympathyDAO.list_search(map);
    return list;
  }
  
  @Override
  public int list_search_count(HashMap<String, Object> map) {
    int cnt = this.sympathyDAO.list_search_count(map);
    return cnt;
  }

  @Override
  public ArrayList<SympathynoticeVO> paging(HashMap<String, Object> map) {
    int begin_of_page = ((int)map.get("now_page") - 1) * Sympathy.RECORD_PER_PAGE;
    
    int start_num = begin_of_page + 1;
    int end_num = begin_of_page + Sympathy.RECORD_PER_PAGE;
    
    map.put("start_num", start_num);
    map.put("end_num", end_num);
    System.out.println("map : " + map);
    ArrayList<SympathynoticeVO> list = this.sympathyDAO.paging(map);
    
    return list;
  }
  
  @Override
  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page,int page_per_block) {
    int total_page = (int) (Math.ceil((double) search_count / record_per_page));
    int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
    int now_grp = (int) (Math.ceil((double) now_page / page_per_block));
    
    int start_page = ((now_grp - 1) * page_per_block) + 1;
    int end_page = (now_grp * page_per_block);
    
    StringBuffer str = new StringBuffer(); 
    
    str.append("<style type='text/css'>");
    str.append("  #paging {text-align: center; margin-top: 5px; font-size: 1em;}");
    str.append("  #paging A:link {text-decoration:none; color:black; font-size: 1em;}");
    str.append("  #paging A:hover{text-decoration:none; background-color: #FFFFFF; color:black; font-size: 1em;}");
    str.append("  #paging A:visited {text-decoration:none;color:black; font-size: 1em;}");
    str.append("  .span_box_1{");
    str.append("    text-align: center;");
    str.append("    font-size: 1em;");
    str.append("    border: 1px;");
    str.append("    border-style: solid;");
    str.append("    border-color: #cccccc;");
    str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/");
    str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/");
    str.append("  }");
    str.append("  .span_box_2{");
    str.append("    text-align: center;");
    str.append("    background-color: #668db4;");
    str.append("    color: #FFFFFF;");
    str.append("    font-size: 1em;");
    str.append("    border: 1px;");
    str.append("    border-style: solid;");
    str.append("    border-color: #cccccc;");
    str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/");
    str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/");
    str.append("  }");
    str.append("</style>");
    str.append("<DIV id='paging'>");
    
    int _now_page = (now_grp - 1) * page_per_block;
    if (now_grp >= 2) { // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
      str.append("<span class='span_box_1'><A href='" + list_file + "?word=" + word + "&now_page=" + _now_page
          + "'>이전</A></span>");
    }
    
    for (int i = start_page; i <= end_page; i++) {
      if (i > total_page) { // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break;
      }

      if (now_page == i) { // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<span class='span_box_2'>" + i + "</span>"); // 현재 페이지, 강조
      } else {
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<span class='span_box_1'><A href='" + list_file + "?word=" + word + "&now_page=" + i + "'>" + i
            + "</A></span>");
      }
    }
    
    _now_page = (now_grp * page_per_block) + 1; // 최대 페이지수 + 1
    if (now_grp < total_grp) {
      str.append("<span class='span_box_1'><A href='" + list_file + "?word=" + word + "&now_page=" + _now_page
          + "'>다음</A></span>");
    }
    str.append("</DIV>");

    return str.toString();
  }



}
