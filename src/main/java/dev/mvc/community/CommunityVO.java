package dev.mvc.community;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE COMMUNITY(
//COMMUNITYNO                       NUMBER(10)     NOT NULL    PRIMARY KEY,
//TITLE                             VARCHAR2(100)    NOT NULL,
//CONTENT                           VARCHAR2(1000)     NOT NULL,
//VCNT                              NUMBER(10)     NOT NULL,
//RCNT                              NUMBER(10)     NOT NULL,
//WRITEDATE                         DATE     NOT NULL,
//TAG                               VARCHAR2(50)     NOT NULL,
//MEMBERNO                          NUMBER(10)     NULL ,
//CTYPENO                           NUMBER(10)     NULL ,
//FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
//FOREIGN KEY (CTYPENO) REFERENCES COMMUNITYCATE (CTYPENO)
//);
@Setter @Getter
public class CommunityVO {
  private int communityno;
  private String title ="";
  private String content= "";
  private int vcnt = 1;
  private int rcnt=0;
  private String writedate;
  private String tag="";
  private int memberno;
  private int ctypeno=3;
  
  
  
  
}
