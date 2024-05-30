package dev.mvc.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.member.MemberDAOInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.memberprofile.MemberprofileProcInter;
import dev.mvc.memberprofile.MemberprofileVO;

@Service("dev.mvc.login.LoginProc")
public class LoginProc implements LoginProcInter {

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.memberprofile.MemberprofileProc")
  private MemberprofileProcInter memberprofileProc;

  MemberVO memberVO;

  @Autowired
  private LoginDAOInter loginDAO;

  @Override
  public int create_login_record(HashMap<String, Object> map) {

    int cnt = this.loginDAO.create_login_record(map);
    return cnt;
  }

  @Override
  public ArrayList<LoginMemberVO> login_list(String word) {
    ArrayList<LoginMemberVO> list = this.loginDAO.login_list(word);
    return list;
  }

  @Override
  public int login_list_no(int loginno) {
    int memberno = this.loginDAO.login_list_no(loginno);

    return memberno;
  }

  @Override
  public int update_null(int memberno) {
    int cnt = this.loginDAO.update_null(memberno);
    return cnt;
  }

  @Override
  public ArrayList<LoginMemberVO> list_by_search_paging(String word, int now_page, int record_per_page) {
    int begin_page = (now_page - 1) * record_per_page;
    int start_num = begin_page+1;
    int end_num = begin_page + record_per_page;   
    
    Map<String,Object> map = new HashMap<String, Object>();
    map.put("word", word);
    map.put("start_num", start_num);
    map.put("end_num", end_num);
    
    ArrayList<LoginMemberVO> list = this.loginDAO.list_by_search_paging(map);
    
    return list;
  }

  @Override
  public int list_by_search_count(String word) {
    int cnt = this.loginDAO.list_by_search_count(word);
    return cnt;
  }
  
  @Override
  public String pagingBox( int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_block) {
    int total_page = (int)(Math.ceil((double)search_count / record_per_page));
    int total_grp = (int)(Math.ceil((double)total_page / page_per_block)); 
    int now_grp = (int)(Math.ceil((double)now_page / page_per_block));  
    
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
    if (now_grp >=2) {
      str.append("<span class='span_box_1'><A href='" + list_file +"?word=" + word + "&now_page=" + _now_page + "'>이전</A></span>");
    }
    System.out.println("_now_page : " + _now_page);
    System.out.println("now_grp : " + now_grp);
    System.out.println("total_page : " + total_page);
    System.out.println("start_page : " + start_page);
    System.out.println("search_count : " + search_count);
    System.out.println("record_per_page : " + record_per_page);
    System.out.println();
    
    for (int i = start_page; i <= end_page;i++) {
      if(i > total_page) {
        break;
      }
      
      if(now_page == i) {
        str.append("<span class='span_box_2'>" + i + "</span>");
      } else {
        str.append("<span class='span_box_1'><A href='" + list_file +"?word=" + word + "&now_page=" + i + "'>" + i + "</A></span>");
      }
    }
    
    _now_page = (now_grp * page_per_block) + 1;
    if (now_grp < total_grp) {
      str.append("<span class='span_box_1'><A href='" + list_file +"?word=" + word + "&now_page=" + _now_page + "'>다음</A></span>");
    }
    str.append("</DIV>");
    
    return str.toString();
  };
  
  @Override
  public LoginVO findnull() {
    
    return null;
  }

}
