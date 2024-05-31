package dev.mvc.matereview;

import java.util.ArrayList;
import java.util.Map;

public interface MateReviewProcInter {
    /**
     * 후기 작성
     * @param mateReviewVO
     * @return
     */
    public int create(MateReviewVO mateReviewVO);

    /**
     * 특정 게시물의 후기 목록 조회
     * @param now_page 현재 페이지
     * @param record_per_page 페이지당 출력 개수
     * @return
     */
    public ArrayList<MateReviewListVO> mateReviewList(int now_page, int record_per_page, int mCommunityNo);

    /**
     * 게시물의 후기 개수
     * @param mCommunityNo 게시물 번호
     * @return
     */
    public int mateReviewListCount(int mCommunityNo);

    /**
     * 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    public String reviewList_pagingBox(int now_page, String list_file, int search_count,
                                     int record_per_page, int page_per_block, int mCommunityNo);

    /**
     * 내가 작성한 후기 리스트 조회
     * @param now_page 현재 페이지
     * @param record_per_page 페이지당 게시물 수
     * @param memberNo 회원 번호
     * @return
     */
    public ArrayList<MyMateReviewVO> myReviewList(int now_page, int record_per_page, int memberNo);

    /**
     * 내가 작성한 후기 개수
     * @param memberNo 회원 번호
     * @return
     */
    public int myReviewCount(int memberNo);

    /**
     * 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    public String myReviewList_pagingBox(int now_page, String list_file, int search_count,
                                       int record_per_page, int page_per_block, int memberNo);

    /**
     * 후기 조회
     * @param rNo 후기 번호
     * @return
     */
    public MateReviewViewVO reviewRead(int rNo);

    /**
     * 후기 삭제
     * @param rNo 후기 번호
     * @return
     */
    public int delete(int rNo);

    /**
     * 후기 수정
     * @param map (별점, 후기내용, 후기번호)
     * @return
     */
    public int update(Map<String, Object> map);

    /**
     * 후기를 작성했는지 확인
     * @param map
     * @return
     */
    public int reviewIsWritten(Map<String,Object> map);
}