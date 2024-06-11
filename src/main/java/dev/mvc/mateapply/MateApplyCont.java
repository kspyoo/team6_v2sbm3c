package dev.mvc.mateapply;

import dev.mvc.matecommunity.MateCommunityJoinVO;
import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.member.MemberProcInter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/mateApply")
@Controller
public class MateApplyCont {

    @Autowired
    @Qualifier("dev.mvc.mateapply.MateApplyProc")
    private MateApplyProcInter mateApplyProc;

    @Autowired
    @Qualifier("dev.mvc.matecommunity.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @Autowired
    @Qualifier("dev.mvc.member.MemberProc")
    private MemberProcInter memberProc;

    @GetMapping("/recruit")
    @ResponseBody
    public int recruit(int mCommunityNo, HttpSession session){
        MateApplyVO mateApplyVO = new MateApplyVO();
        mateApplyVO.setMCommunityNo(mCommunityNo);
        mateApplyVO.setMemberNo((int) session.getAttribute("memberno"));

        return this.mateApplyProc.create(mateApplyVO);
    }

    @GetMapping("/isRecruited")
    @ResponseBody
    public int isRecruited(int mCommunityNo, HttpSession session){
        return this.mateApplyProc.isRecruited((int) session.getAttribute("memberno"), mCommunityNo);
    }

    // 신청 취소
    @GetMapping("/delete")
    @ResponseBody
    public int delete(int mCommunityNo, HttpSession session){
        int cnt = this.mateApplyProc.delete((int) session.getAttribute("memberno"), mCommunityNo);
        System.out.println(cnt);
        return cnt;
    }

    // 내가 보낸 신청 리스트
    @GetMapping("/applyList")
    public String applyList(Model model, HttpSession session,
                            @RequestParam(defaultValue = "0", name = "petTypeNo") int petTypeNo,
                            @RequestParam(defaultValue = "", name = "searchWord") String searchWord,
                            @RequestParam(defaultValue = "1", name = "now_page") int now_page){
        if(session.getAttribute("memberno") != null) {
            // 특정 목록 카테고리 개수
            int applyListCnt = this.mateApplyProc.applyListCnt((int) session.getAttribute("memberno"));
            model.addAttribute("applyListCnt", applyListCnt);

            // 리스트 일련변호
            // 레코드 갯수 - ((현재 페이지 - 1) * 페이지당 레코드 갯수)
            int applyListIndex = (applyListCnt - ((now_page - 1) * MateApply.RECORD_PER_PAGE));
            model.addAttribute("applyListIndex", applyListIndex);

            // 페이징 버튼 목록
            String paging = this.mateApplyProc.list_pagingBox(
                    now_page, searchWord, "/mateApply/applyList", applyListCnt, MateApply.RECORD_PER_PAGE, MateApply.PAGE_PER_BLOCK);

            ArrayList<MateApplyListVO> applyList = this.mateApplyProc.applyList((int) session.getAttribute("memberno"), now_page, MateApply.RECORD_PER_PAGE);

            model.addAttribute("paging", paging);
            model.addAttribute("applyList", applyList);
            model.addAttribute("searchWord", searchWord);
            model.addAttribute("petTypeNo", petTypeNo);
            model.addAttribute("now_page", now_page);

            return "mateApply/my_apply_list";
        }else {
            return "redirect:/member/login";
        }
    }

    // 내가 받은 신청 리스트
    @GetMapping("/recruitList")
    public String recruitList(Model model, HttpSession session, int mCommunityNo,
                              @RequestParam(defaultValue = "0", name = "petTypeNo") int petTypeNo,
                              @RequestParam(defaultValue = "", name = "searchWord") String searchWord,
                              @RequestParam(defaultValue = "1", name = "now_page") int now_page){
        if(session.getAttribute("memberno") != null) {
            // 특정 목록 카테고리 개수
            int recruitListCnt = this.mateApplyProc.recruitListCnt((int)session.getAttribute("memberno"));
            model.addAttribute("recruitListCnt", recruitListCnt);

            // 리스트 일련변호
            // 레코드 갯수 - ((현재 페이지 - 1) * 페이지당 레코드 갯수)
            int recruitListIndex = (recruitListCnt - ((now_page - 1) * MateApply.RECORD_PER_PAGE));
            model.addAttribute("applyListIndex", recruitListIndex);

            // 페이징 버튼 목록
            String paging = this.mateApplyProc.list_pagingBox(
                    now_page, searchWord, "/mateApply/recruitList", recruitListCnt, MateApply.RECORD_PER_PAGE, MateApply.PAGE_PER_BLOCK);

            MateCommunityJoinVO mateCommunityVO = this.mateCommunityProc.read_content(mCommunityNo);
            ArrayList<MateRecruitListVO> recruitList = this.mateApplyProc.recruitList(mCommunityNo,  now_page, MateApply.RECORD_PER_PAGE);

            model.addAttribute("paging", paging);
            model.addAttribute("recruitList", recruitList);
            model.addAttribute("mateCommunityVO", mateCommunityVO);
            model.addAttribute("searchWord", searchWord);
            model.addAttribute("petTypeNo", petTypeNo);
            model.addAttribute("now_page", now_page);

            return "mateApply/recruit_list";
        }else {
            return "redirect:/member/login";
        }
    }

    @GetMapping("/accept")
    public int accept(int aNo){
        return this.mateApplyProc.recruitAccept(aNo);
    }

    @GetMapping("/denied")
    public int denied(int aNo){
        return this.mateApplyProc.recruitDenied(aNo);
    }
}
