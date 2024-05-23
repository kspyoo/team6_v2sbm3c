package dev.mvc.matecommunity;

import java.util.ArrayList;
import java.util.Map;


public interface MateCommunityProcInter {
    /**
     * 게시글 작성
     * @param mateCommunityVO
     * @return
     */
    public int create(MateCommunityVO mateCommunityVO);

    /**
     * 게시글 전체 조회
     * (검색어, 시작번호, 끝번호)
     * @param now_page
     * @param record_per_page
     * @param searchWord 검색어
     * @return
     */
    public ArrayList<MateCommunityVO> list_all(int now_page, int record_per_page, String searchWord);

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
    public String list_all_pagingBox(int now_page, String searchWord, String list_file,
                                     int search_count, int record_per_page, int page_per_block);

    /**
     * 특정 카테고리 전체 리스트 페이징
     * @param searchWord 현재 검색된 검색어 (없으면 "")
     * @param now_page 현재 페이지 번호
     * @param record_per_page 페이지당 레코드 수
     * @param petTypeNo 카테고리번호
     * @return
     */
    public ArrayList<MateCommunityVO> list_all_byPetTypeNo(int now_page, int record_per_page, String searchWord, int petTypeNo);

    /**
     * 특정 카테고리 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param searchWord 현재 검색어
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    public String list_all_byPetTypeNo_pagingBox(
            int now_page, String searchWord, String list_file,
            int search_count, int record_per_page, int page_per_block, int petTypeNo);

    /**
     * 게시글 조회
     * @param mCommunityNo
     * @return
     */
    public MateCommunityVO read_content(int mCommunityNo);

    /**
     * 게시글 수정
     * @param mateCommunityVO (제목, 내용, 검색태그, 시작지점, 모집 인원, 글번호)
     * @return
     */
    public int update_content(MateCommunityVO mateCommunityVO);

    /**
     * 조회수 증가
     * @param mCommunityNo
     * @return
     */
    public int viewCnt_up(int mCommunityNo);

    /**
     * 모집상태 변경(마감)
     * @param mCommunityNo
     * @return
     */
    public int recruit_finish(int mCommunityNo);

    /**
     * 모집상태 변경(모집중)
     * @param mCommunityNo
     * @return
     */
    public int recruit_start(int mCommunityNo);

    /**
     * 게시글 삭제
     * @param mCommunityNo
     * @return
     */
    public int delete_content(int mCommunityNo);

    /**
     * 모든 게시글 갯수
     * @param map
     * @return
     */
    public int list_all_count(String searchWord);

    /**
     * 특정 카테고리의 게시글 갯수
     * @param map (애완동물타입 번호, 검색어)
     * @return
     */
    public int list_all_by_petTypeNo_count(Map<String,Object> map);

}
