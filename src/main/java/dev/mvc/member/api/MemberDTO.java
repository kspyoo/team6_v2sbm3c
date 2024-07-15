package dev.mvc.member.api;

import dev.mvc.member.MemberVO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MemberDTO {
  // api 요청 결과
  private String resultMsg;
  // 유저가 존재할 경우 반환되는 정보
  private MemberVO memberVO;
}
