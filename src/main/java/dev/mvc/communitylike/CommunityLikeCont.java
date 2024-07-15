package dev.mvc.communitylike;

import java.util.HashMap;

import java.util.Map;

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
  private CommunityProcInter comunityProc;

  public CommunityLikeCont() {

  }


  @GetMapping(value = "/read", produces = "application/json")
  @ResponseBody
  public String read(HttpSession session, int communityno, Model model) {
    
    int memberno = (int) session.getAttribute("memberno");
    JSONObject obj = new JSONObject();
    
    HashMap<String, Object>map = new HashMap<>();
    map.put("communityno", communityno);
    map.put("memberno", memberno);
    
    CommunityLikeVO communityLikeVO = this.communityLikeProc.read(map);
    
    attachmentVO attachmentVO = this.comunityProc.read(communityno);
    int rcnt = attachmentVO.getRcnt();
    
    int rcnt_count = this.communityLikeProc.rcnt_count(map); 
    if(rcnt_count ==0) {
     communityLikeProc.create(map);
    }
   
   if(rcnt == 0 ) {
     communityLikeProc.add_update(map);
     rcnt++;
     rcnt_count ++;
   }else {
     communityLikeProc.del_update(map);
     rcnt--;
     rcnt_count --;
   }
   obj.put("rcnt", communityLikeVO.getRcnt());
   obj.put("rcntdate", communityLikeVO.getRcntdate());
   obj.put("communityno", communityLikeVO.getCommunityno());
   obj.put("memberno", communityLikeVO.getMemberno());


    return obj.toString();

  }

}
