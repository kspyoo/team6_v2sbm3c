package dev.mvc.matereview;

import dev.mvc.matecommunity.MateCommunity;
import dev.mvc.matecommunity.MateCommunityJoinVO;
import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.matecommunity.MateCommunityVO;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        MateReviewVO mateReviewVO = new MateReviewVO();
        mateReviewVO.setMemberNo((int) jsonObject.get("memberNo"));
        mateReviewVO.setReviewGrade((int) jsonObject.get("rating"));
        mateReviewVO.setReviewComment((String) jsonObject.get("comment"));
        mateReviewVO.setMCommunityNo((int) jsonObject.get("mCommunityNo"));

        int cnt = this.mateReviewProc.create(mateReviewVO);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @GetMapping("/list")
    public String list_all(Model model, int mCommunityNo, HttpSession session,
                           @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        ArrayList<MateReviewListVO> mateReviewList = this.mateReviewProc.mateReviewList(now_page, MateReview.RECORD_PER_PAGE, mCommunityNo);

        model.addAttribute("mCommunityNo", mCommunityNo);
        model.addAttribute("mateReviewList", mateReviewList);

        int listCount = this.mateReviewProc.mateReviewListCount(mCommunityNo);
        model.addAttribute("listCount", listCount);

        String paging = this.mateReviewProc.reviewList_pagingBox(now_page,"/mateReview/list",listCount
                ,MateReview.RECORD_PER_PAGE, MateCommunity.PAGE_PER_BLOCK,mCommunityNo);
        model.addAttribute("paging", paging);

        model.addAttribute("now_page", now_page);

        return "mateReview/review_list";
    }

    @GetMapping("/myList")
    public String myList_all(Model model, HttpSession session, @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        int memberNo = (int) session.getAttribute("memberno");
        ArrayList<MyMateReviewVO> myReviewList = this.mateReviewProc.myReviewList(now_page, MateReview.RECORD_PER_PAGE, memberNo);

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("myReviewList", myReviewList);

        int listCount = this.mateReviewProc.myReviewCount(memberNo);
        model.addAttribute("listCount", listCount);

        String paging = this.mateReviewProc.reviewList_pagingBox(now_page,"/mateReview/list",listCount
                ,MateReview.RECORD_PER_PAGE, MateCommunity.PAGE_PER_BLOCK,memberNo);
        model.addAttribute("paging", paging);

        model.addAttribute("now_page", now_page);

        return "mateReview/my_review_list";
    }

    @GetMapping("/read")
    public String read(Model model, int rNo, HttpSession session, @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        MateReviewViewVO mateReviewVO = this.mateReviewProc.reviewRead(rNo);
        model.addAttribute("mateReviewVO", mateReviewVO);
        model.addAttribute("now_page", now_page);

        return "mateReview/read";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete(int rNo){
        int cnt = this.mateReviewProc.delete(rNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @GetMapping("/update")
    public String updateForm(int rNo, Model model, @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        MateReviewViewVO mateReviewVO = this.mateReviewProc.reviewRead(rNo);
        MateCommunityJoinVO mateCommunityVO = this.mateCommunityProc.read_content(mateReviewVO.getMCommunityNo());

        model.addAttribute("mateReviewVO", mateReviewVO);
        model.addAttribute("mateCommunityVO", mateCommunityVO);
        model.addAttribute("now_page", now_page);

        return "mateReview/update";
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("rNo", (int) jsonObject.get("rNo"));
        map.put("reviewGrade", (int) jsonObject.get("rating"));
        map.put("reviewComment", (String) jsonObject.get("comment"));

        int cnt = this.mateReviewProc.update(map);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }

    @PostMapping("/reviewCheck")
    @ResponseBody
    public String reviewIsWritten(@RequestBody String json_src, HttpSession session) {
        JSONObject jsonObject = new JSONObject(json_src);
        System.out.println(jsonObject.toString());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberNo", session.getAttribute("memberno"));
        map.put("mCommunityNo", (int) jsonObject.get("mCommunityNo"));

        int cnt = this.mateReviewProc.reviewIsWritten(map);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        return json.toString();
    }
}