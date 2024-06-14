package dev.mvc.notice;

import java.util.Date;

import dev.mvc.member.MemberVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class NoticeVO {
  /** PK*/
  private int noticeno;
  /** 공지글 이름*/
  private String title = "";
  /** 공지글 내용*/
  private String notice = "";
  /** 첨부 파일*/
  private String nfile="";
  /** 접속 일자*/
  private String noticedate;
  /** 비밀 번호*/
  private String passwd;
  /** FK*/
  private Integer masterno=0;
}
  