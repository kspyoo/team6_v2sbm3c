package dev.mvc.community;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
//FILENAME                          VARCHAR2(100)    NOT NULL,
//FILESIZE                          number(10)    NOT NULL,
//THUMBFILE                         VARCHAR2(100)    NOT NULL,
//COMMUNITYNO                       NUMBER(10)     NULL ,

@ToString
@Getter 
@Setter
public class attachmentVO {
  private MultipartFile filenameMF;
  private int cano;
  private String filename;
  private long filesize;
  private String thumbfile;
  private int communityno;
  
  private String title ="";
  private String content= "";
  private int vcnt = 1;
  private int rcnt;
  private String writedate;
  private String tag="";
  private int memberno ;
  private int ctypeno = 3;
  
  
  
}
