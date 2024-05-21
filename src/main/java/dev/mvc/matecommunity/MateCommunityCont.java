package dev.mvc.matecommunity;

import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mateCommunity")
public class MateCommunityCont {

    @Autowired
    @Qualifier("dev.mvc.matecommunity.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @Autowired
    @Qualifier("dev.mvc.member.MemberProc")
    private MemberProcInter memberProcInter;

    @GetMapping("/list_all")
    public String list_all(Model model, HttpSession session,
                           @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                           @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        searchWord = Tool.checkNull(searchWord).trim();

//        ArrayList<PartCateVOMenu> menu = this.partCateProc.menu();
//        model.addAttribute("menu", menu);

        ArrayList<MateCommunityVO> list_all = this.mateCommunityProc.list_all(now_page, MateCommunity.RECORD_PER_PAGE, searchWord);
        model.addAttribute("list_all", list_all);

        // 특정 목록 카테고리 개수
        int mateCommunity_count = this.mateCommunityProc.list_all_count(searchWord);
        model.addAttribute("mateCommunity_cnt", mateCommunity_count);

        // 리스트 일련변호
        // 레코드 갯수 - ((현재 페이지 - 1) * 페이지당 레코드 갯수)
        int mateCommunityIndex = (mateCommunity_count - ((now_page - 1) * MateCommunity.RECORD_PER_PAGE));
        model.addAttribute("mateCommunityIndex", mateCommunityIndex);

        // 페이징 버튼 목록
        String paging = this.mateCommunityProc.list_all_pagingBox(
                now_page, searchWord, "/mateCommunity/list_all", mateCommunity_count, MateCommunity.RECORD_PER_PAGE, MateCommunity.PAGE_PER_BLOCK);

        model.addAttribute("paging", paging);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("now_page", now_page);

        return "mateCommunity/list_all";
    }

    @GetMapping("/list_all_byPetTypeNo")
    public String list_all_byPetTypeNo(Model model, HttpSession session, int petTypeNo,
                           @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                           @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        searchWord = Tool.checkNull(searchWord).trim();

//        ArrayList<PartCateVOMenu> menu = this.partCateProc.menu();
//        model.addAttribute("menu", menu);

        ArrayList<MateCommunityVO> list_all_byPetTypeNo = this.mateCommunityProc.list_all_byPetTypeNo(
                now_page, MateCommunity.RECORD_PER_PAGE, searchWord, petTypeNo);
        model.addAttribute("list_all_byPetTypeNo", list_all_byPetTypeNo);

//        System.out.println("리스트 조회 완료");

        // 특정 목록 카테고리 개수
        Map<String,Object> petType_count_map = new HashMap<String, Object>();
        petType_count_map.put("petTypeNo", petTypeNo);
        petType_count_map.put("searchWord", searchWord);
        int petTypeNo_count = this.mateCommunityProc.list_all_by_petTypeNo_count(petType_count_map);
        model.addAttribute("petTypeNo_count", petTypeNo_count);

//        System.out.println("리스트 개수 조회");

        // 리스트 일련변호
        // 레코드 갯수 - ((현재 페이지 - 1) * 페이지당 레코드 갯수)
        int petTypeNoIndex = (petTypeNo_count - ((now_page - 1) * MateCommunity.RECORD_PER_PAGE));
        model.addAttribute("petTypeNoIndex", petTypeNoIndex);

        // 페이징 버튼 목록
        String paging = this.mateCommunityProc.list_all_byPetTypeNo_pagingBox(
                now_page, searchWord, "/mateCommunity/list_all_byPetTypeNo",
                petTypeNo_count, MateCommunity.RECORD_PER_PAGE, MateCommunity.PAGE_PER_BLOCK, petTypeNo);

//        System.out.println("페이징 제작 완료");

        model.addAttribute("paging", paging);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("now_page", now_page);
        model.addAttribute("petTypeNo", petTypeNo);

        return "mateCommunity/list_all_byPetTypeNo";
    }

    @GetMapping("/create")
    public String createForm(Model model, MateCommunityVO mateCommunityVO, HttpSession session, int petTypeNo){
        model.addAttribute("petTypeNo", petTypeNo);
        model.addAttribute("memberNo", 1);

        return "mateCommunity/create";
    }

    @PostMapping("/create")
    public String create(MateCommunityVO mateCommunityVO){
        String[] tag_list = mateCommunityVO.getSearchTag().split(" ");
        StringBuffer tags = new StringBuffer();

        for (String tag: tag_list){
            tags.append("#");
            tags.append(tag);
            tags.append(" ");
        }

        System.out.println(tags.toString().trim());
        mateCommunityVO.setSearchTag(tags.toString().trim());
        int result = this.mateCommunityProc.create(mateCommunityVO);

        if (result == 1){
            System.out.println("글 작성 성공");
        }else{
            System.out.println("글작성 실패");
        }

        return "redirect:/mateCommunity/list_all";
    }

    @GetMapping("/read")
    public String read(Model model, int mCommunityNo, HttpSession session,
                       @RequestParam(name = "petTypeNo", defaultValue = "0") int petTypeNo,
                       @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                       @RequestParam(name = "now_page", defaultValue = "1") int now_page){
        MateCommunityVO mateCommunityVO= this.mateCommunityProc.read_content(mCommunityNo);
        this.mateCommunityProc.viewCnt_up(mCommunityNo);

        model.addAttribute("mateCommunityVO", mateCommunityVO);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("now_page", now_page);
        model.addAttribute("petTypeNo", petTypeNo);

        return "mateCommunity/read";
    }

}
