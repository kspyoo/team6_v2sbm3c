package dev.mvc.communitylike;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeCommunityVO {
  private int rcnt;
  
  private String rcntdate;
  private int communityno;
  private String title ="";
  private String content= "";
  private int vcnt = 1;
  private String writedate;
  private String tag="";
  private int memberno;
  private int ctypeno;
}
