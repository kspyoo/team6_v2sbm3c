package dev.mvc.communitylike;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter 
public class CommunityLikeVO {

  private int rcnt;
  private int likecheck;
  private String rcntdate;
  private int communityno;
  private int memberno;
}
