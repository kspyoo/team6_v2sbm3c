package dev.mvc.communitylike;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.community.CommunityProcInter;
import dev.mvc.community.attachmentVO;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/communitylike")
@Controller
public class CommunityLikeCont {
  @Autowired
  @Qualifier("dev.mvc.communitylike.CommunityLikeProc")
  private CommunityLikeProcInter communityLikeProc;

  @Autowired
  @Qualifier("dev.mvc.community.CommunityProc")
  private CommunityProcInter communityProc;

  public CommunityLikeCont() {
  }

  @ResponseBody
  @GetMapping(value = "/like")
  public String like(int communityno, HttpSession session, CommunityLikeVO communityLikeVO, Model model) {
    System.out.println("communitylike created");

    int memberno = (int) session.getAttribute("memberno");

    JSONObject json = new JSONObject();

    HashMap<String, Object> map = new HashMap<>();
    map.put("communityno", communityno);
    map.put("memberno", memberno);

    
    CommunityLikeVO existingLike = this.communityLikeProc.read_all(map);

    // 게시물의 현재 좋아요 수를 읽어옵니다.
    attachmentVO attachmentVO = this.communityProc.read(communityno);
    int rcnt = attachmentVO.getRcnt(); // 게시판 좋아요 수

    if (existingLike == null) {
      // 처음 좋아요를 누르는 경우
      communityLikeVO.setLikecheck(1);
      this.communityLikeProc.create(communityLikeVO);
      rcnt++;
      this.communityProc.rcnt_add(map);
    } else {
      int likecheck = existingLike.getLikecheck();
      if (likecheck == 0) {
        // 좋아요 추가
        likecheck++;
        rcnt++;
        this.communityLikeProc.like_check(map);
        this.communityProc.rcnt_add(map);
        System.out.println("likecheck" + likecheck);
        System.out.println("rcnt" + rcnt);
        model.addAttribute("likecheck",likecheck);
      } else {
        // 좋아요 취소
        likecheck--;
        rcnt--;
        this.communityLikeProc.like_check_cancel(map);
        this.communityProc.rcnt_del(map);
        System.out.println("likecheck" + likecheck);
        System.out.println("rcnt" + rcnt);
        model.addAttribute("likecheck",likecheck);
      }
      communityLikeVO.setLikecheck(likecheck);
    }
    
    json.put("communityno", communityno);
    json.put("likecheck", communityLikeVO.getLikecheck());
    json.put("rcnt", rcnt);

    return json.toString();
  }
}