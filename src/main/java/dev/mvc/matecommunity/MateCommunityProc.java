package dev.mvc.matecommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("dev.mvc.matecommunity.MateCommunityProc")
public class MateCommunityProc implements MateCommunityProcInter {

    @Autowired
    private MateCommunityDAOInter mateCommunityDAO;

    @Override
    public int create(MateCommunityVO mateCommunityVO) {
        return mateCommunityDAO.create(mateCommunityVO);
    }

    // ====================================================================================================
    // 페이징 관련 코드
    /**
     * 전체 리스트 페이징
     * @param searchWord 현재 검색된 검색어 (없으면 "")
     * @param now_page 현재 페이지 번호
     * @param record_per_page 페이지당 레코드 수
     * @return
     */
    @Override
    public ArrayList<MateCommunityVO> list_all(int now_page, int record_per_page, String searchWord) {
        // 페이지의 시작 번호
        // ex) 현재페이지 : 2, 페이지당 게시글 갯수 : 3
        // (2 - 1) * 3
        // 1page -> 1,2,3
        // 2page -> 4,5,6
        int begin_of_page = (now_page - 1) * record_per_page;

        // 해당 페이지의 시작 게시글 번호
        // 2page -> 4 -> (3(begin_of_page)+1)
        int start_num = begin_of_page + 1;

        // 페이지의 마지막 리스트 번호
        // 2page -> 6 -> ((3(begin_of_page)+3)
        int end_num = begin_of_page + record_per_page;

        /*
        1 페이지: WHERE r >= 1 AND r <= 10
        2 페이지: WHERE r >= 11 AND r <= 20
        */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchWord",searchWord);
        map.put("start_num", start_num);
        map.put("end_num", end_num);

        ArrayList<MateCommunityVO> listAll = this.mateCommunityDAO.list_all(map);

        return listAll;
    }

    /**
     * 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param searchWord 현재 검색어
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    @Override
    public String list_all_pagingBox(
            int now_page, String searchWord, String list_file,
            int search_count, int record_per_page, int page_per_block) {
        // 전체 페이지수 -> 전체 게시글수 / 페이지당 게시글수 -> 소수점으로 나오면 마지막페이지도 출력해야 되기에 올림처리(ceil)
        int total_page = (int)(Math.ceil((double)search_count / record_per_page));

        // 전체 그룹수 -> 전체 페이지수 / 그룹마다 출력할 페이지 개수 -> 소수점으로 나올땐 마지막 블록도 개수는 충족못하지만 존재한다는 의미이기에 올림처리
        int total_grp = (int)(Math.ceil((double)total_page / page_per_block));

        // 현재 그룹 번호 -> 현재페이지 / 그룹마다 출력할 페이지 개수 -> 그룹의 마지막번호가 현재 페이지일때만 정수로 값이 나옴 올림 처리를 해야지 현재 그룹 번호를 알수있음
        int now_grp = (int)(Math.ceil((double)now_page / page_per_block));

        // 그룹의 첫번호와 끝번호 구하기
        // ((현재 그룹번호 - 1) * 그룹당 페이지번호) +1
        // 1그룹 -> 시작번호 1 ((1 - 1) * 3) +1
        // 2그룹 -> 시작번호 4 ((2 - 1) * 3) +1
        int start_page = ((now_grp - 1) * page_per_block) + 1;
        // 현재 그룹 * 그룹당 페이지 개수
        // 1그룹 -> 마지막 번호 3 (1 * 3)
        // 2그룹 -> 마지막 번호 6 (2 * 3)
        int end_page = (now_grp * page_per_block);

        StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름

        str.append("<DIV id='paging'>");

        // 현재 2그룹일 경우: (2 - 1) * 10 -> 1그룹의 마지막 페이지 10
        // 현재 3그룹일 경우: (3 - 1) * 10 -> 2그룹의 마지막 페이지 20
        // 이전 그룹으로 보내주는 버튼 -> (현재 그룹번호 - 1) * 그룹당 페이지개수 -> 눌렀을때 이전 그룹의 마지막 페이지로 보내줌
        int _now_page = (now_grp - 1) * page_per_block;
        if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
            str.append("<span class='span_box_1'><A href='"+list_file+"?&searchWord="+searchWord+"&now_page="+_now_page+"'> < </A></span>");
        }

        // 중앙의 페이지 목록
        for(int i=start_page; i<=end_page; i++){ // i -> 늘릴 페이지번호
            if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이지 출력 종료
                break;
            }

            if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
                str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조
            }else{
                // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
                str.append("<span class='span_box_1'><A href='"+list_file+"?searchWord="+searchWord+"&now_page="+i+"'>"+i+"</A></span>");
            }
        }

        // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
        // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
        // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
        // 다음그룹으로 보내주는 버튼 -> (현재 그룹 * 그룹당 페이지 개수) + 1 -> 다음 그룹의 첫번째 페이지로 이동
        _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1
        if (now_grp < total_grp){
            str.append("<span class='span_box_1'><A href='"+list_file+"?&searchWord="+searchWord+"&now_page="+_now_page+"'> > </A></span>");
        }
        str.append("</DIV>");

        return str.toString();
    }

    /**
     * 전체 게시글의 게시글 수
     * @param searchWord 검색어
     * @return
     */
    public int list_all_count(String searchWord){
        return mateCommunityDAO.list_all_count(searchWord);
    }

    /**
     * 전체 리스트 페이징
     * @param searchWord 현재 검색된 검색어 (없으면 "")
     * @param now_page 현재 페이지 번호
     * @param record_per_page 페이지당 레코드 수
     * @param petTypeNo 카테고리번호
     * @return
     */
    @Override
    public ArrayList<MateCommunityVO> list_all_byPetTypeNo(int now_page, int record_per_page, String searchWord, int petTypeNo) {
        // 페이지의 시작 번호
        // ex) 현재페이지 : 2, 페이지당 게시글 갯수 : 3
        // (2 - 1) * 3
        // 1page -> 1,2,3
        // 2page -> 4,5,6
        int begin_of_page = (now_page - 1) * record_per_page;

        // 해당 페이지의 시작 게시글 번호
        // 2page -> 4 -> (3(begin_of_page)+1)
        int start_num = begin_of_page + 1;

        // 페이지의 마지막 리스트 번호
        // 2page -> 6 -> ((3(begin_of_page)+3)
        int end_num = begin_of_page + record_per_page;

        /*
        1 페이지: WHERE r >= 1 AND r <= 10
        2 페이지: WHERE r >= 11 AND r <= 20
        */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("petTypeNo",petTypeNo);
        map.put("searchWord",searchWord);
        map.put("start_num", start_num);
        map.put("end_num", end_num);

        ArrayList<MateCommunityVO> listAll = this.mateCommunityDAO.list_all_by_petTypeNo(map);

        return listAll;
    }

    /**
     * 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param searchWord 현재 검색어
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    @Override
    public String list_all_byPetTypeNo_pagingBox(
            int now_page, String searchWord, String list_file,
            int search_count, int record_per_page, int page_per_block, int petTypeNo) {
        // 전체 페이지수 -> 전체 게시글수 / 페이지당 게시글수 -> 소수점으로 나오면 마지막페이지도 출력해야 되기에 올림처리(ceil)
        int total_page = (int)(Math.ceil((double)search_count / record_per_page));

        // 전체 그룹수 -> 전체 페이지수 / 그룹마다 출력할 페이지 개수 -> 소수점으로 나올땐 마지막 블록도 개수는 충족못하지만 존재한다는 의미이기에 올림처리
        int total_grp = (int)(Math.ceil((double)total_page / page_per_block));

        // 현재 그룹 번호 -> 현재페이지 / 그룹마다 출력할 페이지 개수 -> 그룹의 마지막번호가 현재 페이지일때만 정수로 값이 나옴 올림 처리를 해야지 현재 그룹 번호를 알수있음
        int now_grp = (int)(Math.ceil((double)now_page / page_per_block));

        // 그룹의 첫번호와 끝번호 구하기
        // ((현재 그룹번호 - 1) * 그룹당 페이지번호) +1
        // 1그룹 -> 시작번호 1 ((1 - 1) * 3) +1
        // 2그룹 -> 시작번호 4 ((2 - 1) * 3) +1
        int start_page = ((now_grp - 1) * page_per_block) + 1;
        // 현재 그룹 * 그룹당 페이지 개수
        // 1그룹 -> 마지막 번호 3 (1 * 3)
        // 2그룹 -> 마지막 번호 6 (2 * 3)
        int end_page = (now_grp * page_per_block);

        StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름

        str.append("<DIV id='paging'>");

        // 현재 2그룹일 경우: (2 - 1) * 10 -> 1그룹의 마지막 페이지 10
        // 현재 3그룹일 경우: (3 - 1) * 10 -> 2그룹의 마지막 페이지 20
        // 이전 그룹으로 보내주는 버튼 -> (현재 그룹번호 - 1) * 그룹당 페이지개수 -> 눌렀을때 이전 그룹의 마지막 페이지로 보내줌
        int _now_page = (now_grp - 1) * page_per_block;
        if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
            str.append("<span class='span_box_1'><A href='"+list_file+"?petTypeNo="+petTypeNo+"&searchWord="+searchWord+"&now_page="+_now_page+"'> < </A></span>");
        }

        // 중앙의 페이지 목록
        for(int i=start_page; i<=end_page; i++){ // i -> 늘릴 페이지번호
            if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이지 출력 종료
                break;
            }

            if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
                str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조
            }else{
                // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
                str.append("<span class='span_box_1'><A href='"+list_file+"?petTypeNo="+petTypeNo+"&searchWord="+searchWord+"&now_page="+i+"'>"+i+"</A></span>");
            }
        }

        // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
        // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
        // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
        // 다음그룹으로 보내주는 버튼 -> (현재 그룹 * 그룹당 페이지 개수) + 1 -> 다음 그룹의 첫번째 페이지로 이동
        _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1
        if (now_grp < total_grp){
            str.append("<span class='span_box_1'><A href='"+list_file+"?petTypeNo="+petTypeNo+"&searchWord="+searchWord+"&now_page="+_now_page+"'> > </A></span>");
        }
        str.append("</DIV>");

        return str.toString();
    }

    /**
     *어
     * @param map
     * @return
     */
    @Override
    public int list_all_by_petTypeNo_count(Map<String, Object> map) {
        return mateCommunityDAO.list_all_by_petTypeNo_count(map);
    }

    // ====================================================================================================

    @Override
    public MateCommunityVO read_content(int mCommunityNo) {
        return mateCommunityDAO.read_content(mCommunityNo);
    }

    @Override
    public int update_content(Map<String, Object> map) {
        return mateCommunityDAO.update_content(map);
    }

    @Override
    public int viewCnt_up(int mCommunityNo) {
        return mateCommunityDAO.viewCnt_up(mCommunityNo);
    }

    @Override
    public int recruit_finish(int mCommunityNo) {
        return mateCommunityDAO.recruit_finish(mCommunityNo);
    }

    @Override
    public int recruit_start(int mCommunityNo) {
        return mateCommunityDAO.recruit_start(mCommunityNo);
    }

    @Override
    public int delete_content(int mCommunityNo) {
        return mateCommunityDAO.delete_content(mCommunityNo);
    }
}
