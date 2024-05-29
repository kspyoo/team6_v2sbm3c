package dev.mvc.catecommunity;

import lombok.Getter;
import lombok.Setter;

//CTYPENO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
//CNT                               NUMBER(10)     DEFAULT 0     NOT NULL,
//TYPENAME                          VARCHAR2(100)    NOT NULL,
//MASTERNO                          NUMBER(10)     NULL ,
//FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
@Getter @Setter
public class CateCommunityVO {

  private int ctypeno;
  private int cnt =0;
  private String typename ="";
  private int masterno;
}
