package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.login.LoginMemberVO;
import dev.mvc.memberprofile.MemberprofileVO;
import dev.mvc.tool.Security;


@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {
  
  @Autowired
  private MemberDAOInter memberDAO;
  
  @Autowired
  Security security;
  
  public MemberProc() {
    
  }
  
  @Override
  public int checkID(String id) {
    int cnt = this.memberDAO.checkID(id);
    return cnt;
  }
  
  @Override
  public int create(MemberVO memberVO) {
    String passwd = memberVO.getPasswd();
    
    String passwd_encoded = this.security.aesEncode(passwd);
    memberVO.setPasswd(passwd_encoded);
    
    int cnt = this.memberDAO.create(memberVO);
    
    
    return cnt;
  }
  
  @Override
  public MemberVO readById(String id) {
    MemberVO memberVO = this.memberDAO.readById(id);
    return memberVO;
  }
  
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.memberDAO.login(map);
    return cnt;
  }
  
  @Override
  public int update(MemberVO memberVO) {
    int cnt = this.memberDAO.update(memberVO);
    return cnt;
  }
  
  @Override
  public MemberVO read(int memberno) {
    MemberVO memberVO = this.memberDAO.read(memberno);
    return memberVO;
  }
  
  @Override
  public int delete(int memberno) {
    int cnt = this.memberDAO.delete(memberno);
    return cnt;
  }
  
  @Override
  public int delete_FK(int memberno) {
    int cnt = this.memberDAO.delete_FK(memberno);
    return cnt;
  }

  @Override
  public MemberVO findId(@Param("name") String name, @Param("phone") String phone) {
    MemberVO memberVO = this.memberDAO.findId(name,phone);
    return memberVO;
  }
  
  @Override
  public int findIdCheck(HashMap<String, Object> map) {
    int cnt = this.memberDAO.findIdCheck(map);
    return cnt;
  }
  
  @Override
  public MemberVO findPasswd(@Param("name") String name, @Param("phone") String phone, @Param("id") String id) {
    MemberVO memberVO = this.memberDAO.findId(name,phone);
    return memberVO;
  }
  
  @Override
  public int findPasswdCheck(HashMap<String, Object> map) {
    int cnt = this.memberDAO.findPasswdCheck(map);
    return cnt;
  }
  
  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_update(map);
    return cnt;
  }
  
  @Override
  public ArrayList<MemberVO> list() {
    ArrayList<MemberVO> list = this.memberDAO.list();
    return list;
  }
  
  @Override
  public ArrayList<MemberVO> list_no(int memberno) {
    ArrayList<MemberVO> list = this.memberDAO.list_no(memberno);
    return list;
  }
  
  @Override
  public int checkPhone(String phone) {
    int cnt = this.memberDAO.checkPhone(phone);
    return cnt;
  }

  @Override
  public ArrayList<MemberVO> list_paging(String word, int now_page,int record_per_page) {
    int begin_page = (now_page - 1) * record_per_page;
    int start_num = begin_page+1;
    int end_num = begin_page + record_per_page;   
    
    Map<String,Object> map = new HashMap<String, Object>();
    map.put("word", word);
    map.put("start_num", start_num);
    map.put("end_num", end_num);
    
    ArrayList<MemberVO> list = this.memberDAO.list_paging(map);
    
    return list;
  }

  @Override
  public int search_count(String word) {
    int cnt = this.memberDAO.search_count(word);
    return 0;
  }
  
  @Override
  public String pagingBox( int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_block,int masterno) {
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
      str.append("<span class='span_box_1'><A href='" + list_file +"?masterno=" + masterno + "word=" + word + "&now_page=" + _now_page + "'>이전</A></span>");
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
        str.append("<span class='span_box_1'><A href='" + list_file +"?masterno=" + masterno+"word=" + word + "&now_page=" + i + "'>" + i + "</A></span>");
      }
    }
    
    _now_page = (now_grp * page_per_block) + 1;
    if (now_grp < total_grp) {
      str.append("<span class='span_box_1'><A href='" + list_file +"?masterno=" + masterno+"word=" + word + "&now_page=" + _now_page + "'>다음</A></span>");
    }
    str.append("</DIV>");
    
    return str.toString();
  };

}
