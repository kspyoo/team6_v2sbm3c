package dev.mvc.communityattachment;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
//FILENAME                          VARCHAR2(100)    NOT NULL,
//FILESIZE                          number(10)    NOT NULL,
//THUMBFILE                         VARCHAR2(100)    NOT NULL,
//COMMUNITYNO                       NUMBER(10)     NULL ,



@Getter 
@Setter
@ToString

public class CommunityattachmentVO {
  private MultipartFile filenameMF;
  private int cano;
  private String filename;
  private long filesize;
  private String thumbfile;
  private int communityno;

  
  
}
