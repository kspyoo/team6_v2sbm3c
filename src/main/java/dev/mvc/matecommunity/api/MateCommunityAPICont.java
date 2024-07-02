package dev.mvc.matecommunity.api;

import dev.mvc.mateapply.MateApplyProcInter;
import dev.mvc.matecommunity.MateCommunity;
import dev.mvc.matecommunity.MateCommunityJoinVO;
import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.matecommunity.MateCommunityVO;
import dev.mvc.pettype.PetTypeProcInter;
import dev.mvc.pettype.PetTypeVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/mateCommunity")
public class MateCommunityAPICont {

    @Autowired
    @Qualifier("dev.mvc.matecommunity.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @Autowired
    @Qualifier("dev.mvc.mateapply.MateApplyProc")
    private MateApplyProcInter mateApplyProc;

    @Autowired
    @Qualifier("dev.mvc.pettype.PetTypeProc")
    private PetTypeProcInter PetTypeProc;

    // 게시글 전체 리스트 조회 + 페이징 + 검색
    @GetMapping(value = {"/list_all/{cateNo}/{nowPage}/{searchWord}", "/list_all/{cateNo}/{nowPage}"})
    public ResponseEntity<List<MateCommunityVO>> communityList(@PathVariable(name = "cateNo") int cateNo,
                                              @PathVariable(name = "searchWord", required = false) String searchWord,
                                              @PathVariable(name = "nowPage") int nowPage){
        searchWord = Tool.checkNull(searchWord).trim();

        ArrayList<MateCommunityVO> list_all = new ArrayList<MateCommunityVO>();

        if (cateNo == 0){
            list_all = this.mateCommunityProc.list_all(
                    nowPage, MateCommunity.RECORD_PER_PAGE, searchWord);
        }else{
            list_all = this.mateCommunityProc.list_all_byPetTypeNo(
                    nowPage, MateCommunity.RECORD_PER_PAGE, searchWord, cateNo);
        }

        return ResponseEntity.status(HttpStatus.OK).body(list_all);
    }

    @GetMapping(value = {"/list_count/{cateNo}/{searchWord}", "/list_count/{cateNo}"})
    public String listCount(@PathVariable(name = "cateNo") int cateNo,
                            @PathVariable(name = "searchWord", required = false) String searchWord){
        // 특정 목록 카테고리 개수
        int mateCommunity_count = this.mateCommunityProc.list_all_count(searchWord);

        return "";
    }

    @GetMapping(value={"/page_count/{cateNo}/{searchWord}", "/page_count/{cateNo}"})
    public ResponseEntity<Map<String, Object>> pageCount(@PathVariable(name = "cateNo") int cateNo,
                            @PathVariable(name = "searchWord", required = false) String searchWord) {
        searchWord = Tool.checkNull(searchWord).trim();
        int page_count = 1;

        if (cateNo == 0) {
            page_count = this.mateCommunityProc.list_all_count(searchWord);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("petTypeNo", cateNo);
            map.put("searchWord", searchWord);
            page_count = this.mateCommunityProc.list_all_by_petTypeNo_count(map);
        }

        page_count = (int) Math.ceil((float) page_count / MateCommunity.RECORD_PER_PAGE);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagingCount", page_count);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/isRecruited/{memberNo}/{mCommunityNo}")
    public ResponseEntity<Map<String, Object>> isRecruited(@PathVariable("memberNo") int memberNo, @PathVariable("mCommunityNo") int mCommunityNo){
        int isRecruited = this.mateApplyProc.isRecruited(memberNo, mCommunityNo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isRecruited", isRecruited);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    // 게시글 작성
    @PostMapping("/create")
    public String create(MateCommunityVO mateCommunityVO, HttpSession session){
        if (session.getAttribute("memberno")!=null) {
            StringBuffer tags = new StringBuffer(" ");

            if (String.valueOf(mateCommunityVO.getSearchTag()).trim() != "" || String.valueOf(mateCommunityVO.getSearchTag()) != "null") {
                String[] tag_list = mateCommunityVO.getSearchTag().split(" ");
                for (String tag : tag_list) {
                    tags.append("#");
                    tags.append(tag);
                    tags.append(" ");
                }
            }

            System.out.println(tags.toString().trim());
            mateCommunityVO.setSearchTag(tags.toString().trim());
            int result = this.mateCommunityProc.create(mateCommunityVO);

            if (result == 1) {
                System.out.println("글 작성 성공");
            } else {
                System.out.println("글작성 실패");
            }

            return "redirect:/mateCommunity/list_all";
        }else{
            return "redirect:/member/login";
        }
    }

    // 게시글 조회
    @GetMapping("/read/{mCommunityNo}")
    public ResponseEntity<MateCommunityJoinVO> read(@PathVariable("mCommunityNo") int mCommunityNo){
        this.mateCommunityProc.viewCnt_up(mCommunityNo);

        MateCommunityJoinVO mateCommunityVO= this.mateCommunityProc.read_content(mCommunityNo);
        System.out.println(mateCommunityVO.getMCommunityNo());

        return ResponseEntity.status(HttpStatus.OK).body(mateCommunityVO);
    }

    @GetMapping("/viewCnt_up/{mCommunityNo}")
    public ResponseEntity<Map<String, Object>> viewCnt_up(@PathVariable("mCommunityNo") int mCommunityNo){
        int result = this.mateCommunityProc.viewCnt_up(mCommunityNo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    // 게시글 수정 폼
    @GetMapping("/update")
    public String updateForm(Model model, int mCommunityNo, HttpSession session,
                         @RequestParam(name = "petTypeNo", defaultValue = "0") int petTypeNo){
        if (session.getAttribute("memberno")!=null) {

            MateCommunityJoinVO mateCommunityVO = this.mateCommunityProc.read_content(mCommunityNo);

            StringBuffer tags = new StringBuffer();

            if (mateCommunityVO.getSearchTag().trim() != "") {
                String[] searchTags = mateCommunityVO.getSearchTag().split("#");
                for (String tag : searchTags) {
                    tags.append(tag);
                }
            }

            System.out.println(tags.toString().trim());
            mateCommunityVO.setSearchTag(tags.toString().trim());

            model.addAttribute("mateCommunityVO", mateCommunityVO);
            model.addAttribute("petTypeNo", petTypeNo);

            System.out.println(petTypeNo);

            return "mateCommunity/update";
        }else{
            return "redirect:/member/login";
        }
    }

    // 게시글 수정
    @PostMapping("/update")
    public String update(Model model, MateCommunityVO mateCommunityVO, HttpSession session,
                         @RequestParam(name = "current_petTypeNo", defaultValue = "0") int current_petTypeNo){
        if (session.getAttribute("memberno")!=null) {
            StringBuffer tags = new StringBuffer(" ");
            if (String.valueOf(mateCommunityVO.getSearchTag()).trim() != "" || String.valueOf(mateCommunityVO.getSearchTag()) != "null") {
                String[] tag_list = mateCommunityVO.getSearchTag().split(" ");
                for (String tag : tag_list) {
                    tags.append("#");
                    tags.append(tag);
                    tags.append(" ");
                }
            }

            mateCommunityVO.setSearchTag(tags.toString().trim());

            int result = this.mateCommunityProc.update_content(mateCommunityVO);

            if (result == 1) {
                System.out.println("글 수정 성공");
            } else {
                System.out.println("글 수정 실패");
            }

            if (current_petTypeNo == 0) {
                return "redirect:/mateCommunity/read?mCommunityNo=" + mateCommunityVO.getMCommunityNo();
            } else {
                return "redirect:/mateCommunity/read?petTypeNo=" + current_petTypeNo + "&mCommunityNo=" + mateCommunityVO.getMCommunityNo();
            }
        }else{
            return "redirect:/member/login";
        }
    }

    // 게시글 삭제
    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody String json_src){
        JSONObject jsonObject = new JSONObject(json_src);
        int mCommunityNo = (int) jsonObject.get("mCommunityNo");

        this.mateApplyProc.delete_byCommunityNo(mCommunityNo);
        int cnt = this.mateCommunityProc.delete_content(mCommunityNo);

        JSONObject json = new JSONObject();
        json.put("cnt", cnt);

        System.out.println(cnt);

        return json.toString();
    }

    // 모집 종료로 상태 변경
    @GetMapping("/recruit_finish")
    @ResponseBody
    public int  recruit_finish(int mCommunityNo, HttpSession session){
        int cnt = this.mateCommunityProc.recruit_finish(mCommunityNo);
        if (cnt == 1){
            this.mateApplyProc.deniedAll(mCommunityNo);
        }

        System.out.println(cnt);
        return cnt;
    }

    // 모집중으로 상태 변경
    @GetMapping("/recruit_start")
    @ResponseBody
    public int recruit_start(int mCommunityNo, HttpSession session){
        int cnt = this.mateCommunityProc.recruit_start(mCommunityNo);
        System.out.println(cnt);
        return cnt;
    }
}
