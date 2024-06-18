package dev.mvc.noticeimg;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class NoticeimgVO {
  
  /***
   *    imgno                         NUMBER(10)    NOT NULL     PRIMARY KEY,
    nfile                       VARCHAR2(100)    NULL ,
    filesaved                           VARCHAR2(100)    NULL ,
    thumbfile                         VARCHAR2(100)    NULL ,
    filesize                        LONG     NULL ,
    noticeno                          NUMBER(10)    NOT NULL ,
   */
  
  
  /** PK*/
  private int imgno;
  /** 공지글 이름*/
  private String nfile = "";
  /** 공지글 내용*/
  private String filesaved = "";
  /** 첨부 파일*/
  private String thumbfile="";
  /** 메인 이미지 크기 */
  private long filesize = 0;
  /** FK*/
  private Integer noticeno=0;
  
  private List<MultipartFile> fnamesMF ;

}