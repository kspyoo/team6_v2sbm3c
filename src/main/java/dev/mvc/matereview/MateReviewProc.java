package dev.mvc.matereview;

import dev.mvc.matecommunity.MateCommunityJoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("dev.mvc.matereview.MateReviewProc")
public class MateReviewProc implements MateReviewProcInter{

    @Autowired
    private MateReviewDAOInter mateReviewDAOInter;

    @Override
    public int create(MateReviewVO mateReviewVO) {
        return mateReviewDAOInter.create(mateReviewVO);
    }

    @Override
    public ArrayList<MateReviewListVO> mateReviewList(int now_page, int record_per_page, int mCommunityNo) {
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
        map.put("mCommunityNo", mCommunityNo);
        map.put("start_num", start_num);
        map.put("end_num", end_num);

        ArrayList<MateReviewListVO> mateReviewList = this.mateReviewDAOInter.mateReviewList(map);
        return mateReviewList;
    }

    @Override
    public int mateReviewListCount(int mCommunityNo) {
        return this.mateReviewDAOInter.mateReviewListCount(mCommunityNo);
    }

    //특정 게시물의 후기 리스트 하단 페이징
    @Override
    public String reviewList_pagingBox(int now_page, String list_file, int search_count,
                                       int record_per_page, int page_per_block, int mCommunityNo) {
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
            str.append("<span class='span_box_1'><A href='"+list_file+"?mCommunityNo="+ mCommunityNo + "&now_page="+_now_page+"'> < </A></span>");
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
                str.append("<span class='span_box_1'><A href='"+list_file+"?mCommunityNo="+ mCommunityNo+"&now_page="+i+"'>"+i+"</A></span>");
            }
        }

        // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
        // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
        // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
        // 다음그룹으로 보내주는 버튼 -> (현재 그룹 * 그룹당 페이지 개수) + 1 -> 다음 그룹의 첫번째 페이지로 이동
        _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1
        if (now_grp < total_grp){
            str.append("<span class='span_box_1'><A href='"+list_file+"?mCommunityNo="+ mCommunityNo+"&now_page="+_now_page+"'> > </A></span>");
        }
        str.append("</DIV>");

        return str.toString();
    }

    @Override
    public ArrayList<MyMateReviewVO> myReviewList(int now_page, int record_per_page, int memberNo) {
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
        map.put("memberNo", memberNo);
        map.put("start_num", start_num);
        map.put("end_num", end_num);

        ArrayList<MyMateReviewVO> myReviewList = this.mateReviewDAOInter.myReviewList(map);
        return myReviewList;
    }

    @Override
    public int myReviewCount(int mCommunityNo) {
        return this.mateReviewDAOInter.myReviewCount(mCommunityNo);
    }

    //특정 게시물의 후기 리스트 하단 페이징
    @Override
    public String myReviewList_pagingBox(int now_page, String list_file, int search_count,
                                       int record_per_page, int page_per_block, int memberNo) {
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
            str.append("<span class='span_box_1'><A href='"+list_file+"?memberNo="+ memberNo + "&now_page="+_now_page+"'> < </A></span>");
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
                str.append("<span class='span_box_1'><A href='"+list_file+"?memberNo="+ memberNo+"&now_page="+i+"'>"+i+"</A></span>");
            }
        }

        // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
        // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
        // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
        // 다음그룹으로 보내주는 버튼 -> (현재 그룹 * 그룹당 페이지 개수) + 1 -> 다음 그룹의 첫번째 페이지로 이동
        _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1
        if (now_grp < total_grp){
            str.append("<span class='span_box_1'><A href='"+list_file+"?memberNo="+ memberNo+"&now_page="+_now_page+"'> > </A></span>");
        }
        str.append("</DIV>");

        return str.toString();
    }

    @Override
    public MateReviewViewVO reviewRead(int rNo){
        return this.mateReviewDAOInter.reviewRead(rNo);
    }

    @Override
    public int delete(int rNo){
        return this.mateReviewDAOInter.delete(rNo);
    }

    @Override
    public int update(Map<String, Object> map){
        return this.mateReviewDAOInter.update(map);
    }

    @Override
    public int reviewIsWritten(Map<String, Object> map){
        return this.mateReviewDAOInter.reviewIsWritten(map);
    }
}