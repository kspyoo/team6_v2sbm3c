package dev.mvc.matereview;

import dev.mvc.matecommunity.MateCommunityJoinVO;
import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.matecommunity.MateCommunityVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mateReview")
public class MateReviewCont {

    @Autowired
    @Qualifier("dev.mvc.matereview.MateReviewProc")
    private MateReviewProcInter mateReviewProc;

    @Autowired
    @Qualifier("dev.mvc.matecommunity.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @GetMapping("/create")
    public String createForm(@RequestParam(name = "mCommunityNo") int mCommunityNo, Model model, HttpSession session) {
        MateCommunityJoinVO mateCommunityVO = this.mateCommunityProc.read_content(mCommunityNo);
        model.addAttribute("mCommunityNo", mCommunityNo);
        model.addAttribute("memberNo", session.getAttribute("memberno"));
        model.addAttribute("mateCommunityVO", mateCommunityVO);

        return "mateReview/create";
    }
}